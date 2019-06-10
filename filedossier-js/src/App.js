import React, { Component } from 'react';
import Dossier from './Dossier';
import Example from './Example';
import './App.css';

class App extends Component {

    render() {
        return (
                <div className="app">
                    <Dossier dossierKey="123" dossierPackage="testmodel" dossierCode="TEST"/>
                    <Example/>
                </div>
                );
    }
}

export default App;
