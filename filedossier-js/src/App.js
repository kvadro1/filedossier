import React, { Component } from 'react';
import FileDossier from './FileDossier';
import './App.css';

class App extends Component {

    render() {
        return (
                <div className="app">
                    <FileDossier dossierKey="123" dossierPackage="testmodel" dossierCode="TEST"/>
                </div>
                );
    }
}

export default App;
