import React, { useState, useEffect } from 'react';
import { Table, Button } from 'semantic-ui-react'

function FileDossier( { dossierKey, dossierPackage, dossierCode }) {
    const [dossier, setDossier] = useState(null);


    useEffect(() => {
        async function fetchData() {
            const response = await fetch('/filedossier-web/web/dossiers/' + dossierKey + '/' + dossierPackage + '/' + dossierCode + '.json'); // Uses dossierId prop
            const json = await response.json();
            setDossier(json);
        }
        fetchData();
    }, [dossierKey, dossierPackage, dossierCode]); // Or [] if effect doesn't need props or state


        return (
                <div className="fileDosser">
                    {dossier && <div>
                        Name: {dossier.name}
                        {dossier.dossierFile &&
                                            <Table celled>
                                                <Table.Header>
                                                    <Table.Row>
                                                        <Table.HeaderCell>Файл</Table.HeaderCell>
                                                        <Table.HeaderCell>Действие</Table.HeaderCell>
                                                    </Table.Row>
                                                </Table.Header>

                                                <Table.Body>
                                                    {dossier.dossierFile.map((file, index) => <Table.Row key={index}>
                                                        <Table.Cell>{file.name}</Table.Cell>
                                                        <Table.Cell><Button content="Удалить"/></Table.Cell>
                                                    </Table.Row>)}
                                                </Table.Body>
                                            </Table>
                        }



                    </div>}
                </div>
                );
    


    //return <div>1111</div>;
}

export default FileDossier;