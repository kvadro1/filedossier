import { executeFileApi } from '@ilb/js-auto-proxy';
import { createDossierApi } from '../../../../conf/config';

export default async (req, res) => {
  const xRemoteUser = req && req.headers && req.headers['x-remote-user'];
  const apiDossier = createDossierApi(xRemoteUser);
  executeFileApi(apiDossier, req, res);
};

export const config = {
  api: {
    bodyParser: false,
  },
};
