import { useState } from 'react';
import { createJsProxy } from '@ilb/js-auto-proxy';
import { createDossierApi, getProxyApiClient } from '../conf/config';

export default class FileDossier {
  constructor ({ dossierParams, xRemoteUser } = {}) {
    this.dossierParams = dossierParams; // { dossierKey, dossierPackage, dossierCode, dossierMode };

    const apiDossier = createDossierApi(xRemoteUser);
    this.apiDossier = createJsProxy(apiDossier, 'dossier');

    const proxyApiClient = getProxyApiClient(xRemoteUser);
    this.proxyApiClient = createJsProxy(proxyApiClient, 'proxy');
  }

  /* return array of dossier params */
  getDossierParams = () => {
    const keysOrder = ['dossierKey', 'dossierPackage', 'dossierCode', 'dossierMode'];
    const dossierParamsArray = keysOrder.map(key => this.dossierParams[key]);
    return dossierParamsArray;
  };

  getFileType = (file) => {
    if (file.mediaType) {
      if (file.mediaType.indexOf('image/') === 0) {
        return 'image';
      } else if (file.mediaType === 'application/pdf') {
        return 'pdf';
      }
    }
  };

  getFileLink = ({ file, inline }) => {
    const { dossierKey, dossierPackage, dossierCode, dossierMode } = this.dossierParams;
    if (file.exists) {
      return `https://devel.net.ilb.ru/workflow-web/web/v2/` +
      `dossiers/${dossierKey}/${dossierPackage}/${dossierCode}/${dossierMode}/dossierfiles/${file.code}` +
      `?nocache=${(file.lastModified || '').replace(/\D/g, '')}` +
      `${inline ? `&mode=inline` : 'attachment'}`;
    }
  };

  getFileAccept = (file) => {
    if (file && file.allowedMediaTypes && file.allowedMediaTypes.allowedMediaType) {
      return file.allowedMediaTypes.allowedMediaType.join(',');
    }
  };

  /** creates uniq string based on dossierFile and dossierParams */
  getFileUniqId = (file) => {
    const { dossierKey, dossierPackage, dossierCode } = this.dossierParams;
    return `file_${dossierKey}_${dossierPackage}_${dossierCode}_${file.code}`;
  };

  prepareDossier = (dossier) => {
    if (dossier.dossierFile && dossier.dossierFile.length) {
      dossier.dossierFile = dossier.dossierFile.map(file => ({
        ...file,
        type: this.getFileType(file),
        path: this.getFileLink({ file, inline: false }),
        inlinePath: this.getFileLink({ file, inline: true }),
        accept: this.getFileAccept(file),
        uniqId: this.getFileUniqId(file),
      }));
    }
    return dossier;
  };

  getFileTypeByExt = (ext) => {
    if (ext) {
      if (['jpeg', 'jpg', 'jpe', 'png'].indexOf(ext.toLowerCase()) !== -1) {
        return 'image';
      } else if (ext === 'pdf') {
        return 'pdf';
      }
    }
  };

  parseExternalFile = (file) => ({
    ...file,
    type: this.getFileTypeByExt(file.ext),
    path: file.path + '&mode=attachment',
    lastModified: file.date_create,
    inlinePath: file.path + '&mode=inline',
  });

  getDossier = async () => {
    var { response: dossier, error } = await this.apiDossier.getDossier(...this.getDossierParams());
    if (dossier) { dossier = this.prepareDossier(dossier); }
    let result = { dossierParams: this.dossierParams, dossier, error };

    if (!error && this.dossierParams.externalDossier) {
      var { response: external, error: externalError } = await this.proxyApiClient.get(this.dossierParams.externalDossier);
      if (external && external.length) { external = external.map(this.parseExternalFile); }
      result = { ...result, external, error: externalError };
    }

    return result;
  }

  // download file
  download = async ({ fileCode, version, mode }) => {
    const response = await this.apiDossier.download(fileCode, ...this.getDossierParams(), { version, mode });
    return response;
  }

  /* создает новую версию файла */
  publish = async ({ fileCode, file }) => {
    const response = await this.apiDossier.publish(file, [fileCode, ...this.getDossierParams()]);
    return response;
  }

  /* создает новую версию файла (для использования на серверной стороне, без изменения порядка аргyментов) */
  publish1 = async ({ fileCode, file }) => {
    const response = await this.apiDossier.publish(fileCode, ...this.getDossierParams(), { file });
    return response;
  }

  /* сохраняет файл в текущую версию */
  update = async ({ fileCode, file }) => {
    const response = await this.apiDossier.update(file, [fileCode, ...this.getDossierParams()]);
    return response;
  }

  /* import file from url */
  importFile = async ({ fileCode, url }) => {
    /* Here we gonna convert file to base64 (on download) and back to buffer (before publish) */
    const fileResult = await this.proxyApiClient.get(encodeURI(url), { accept: '*/*', returnType: 'Base64' });
    if (fileResult.error || !fileResult.response) {
      return fileResult;
    }

    const file = new Buffer(fileResult.response, 'base64');
    const uploadMethod = process.browser ? 'publish' : 'publish1';
    const importResult = await this[uploadMethod]({ fileCode, file });
    return importResult;
  }

  /**
   * Создает асинхронный метод-обертку для вызова сервисов с последующим обновлением данных досье
   * соответственно управляет переменными ззапросов loading и error
   * @param {object} state - request state from useDossier
   * @param {function} setState - function to update state
   * @param {function} action - async function with some request action
   * @param {boolean} withUpdate - flag of necessity to call getDossier after action
   */
  _createRequestAction = ({ state, setState, action, withUpdate }) => async (...params) => {
    setState({ ...state, loading: true, error: null });
    const result = {};
    if (action) {
      const result = await action(...params);
      if (result.error) {
        setState({ ...state, loading: false, error: result.error });
        return result;
      }
    }

    if (withUpdate) { // update dossier
      const dossierData = await this.getDossier();
      setState({ ...state, dossierData, loading: false, error: null });
    } else {
      setState({ ...state, loading: false, error: null });
    }
    return result;
  };

  useDossier = (dossierData = {}) => {
    const [state, setState] = useState({ dossierData, loading: false, error: null }); // init state
    const dossierActions = {
      updateDossier: this._createRequestAction({ state, setState, withUpdate: true }),
      publish: this._createRequestAction({ state, setState, action: this.publish.bind(this), withUpdate: true }),
      update: this._createRequestAction({ state, setState, action: this.update.bind(this), withUpdate: true }),
      importFile: this._createRequestAction({ state, setState, action: this.importFile.bind(this), withUpdate: true }),
      resetHook: () => { setState({ ...state, loading: false, error: null }); },
    };
    return [state, dossierActions];
  }
}
