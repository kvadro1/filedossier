import React, { Component } from 'react';
import FileDossier from './FileDossier2';
import Example from './Example';
import './App.css';

class App extends Component {

    render() {
        return (
                <div className="app">
                    <FileDossier dossierKey="123" dossierPackage="testmodel" dossierCode="TEST"/>
                    <Example/>
                </div>
                );
    }
}

export default App;
