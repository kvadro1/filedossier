import React, { Component } from 'react';
import Dossier from './Dossier';
import Example from './Example';
//import DisplayRemoteData from './DisplayRemoteData';
import './App.css';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

function Index() {
    return (<div className="app">
        <Dossier dossierKey="123" dossierPackage="testmodel" dossierCode="TEST"/>
        <Example/>
    </div>
            );
}

function Activity(props) {
    console.log(props.match.params);
    return (<div className="app">
    </div>
            );
}

class App extends Component {

    render() {
        return (
                <Router>
                    <div>
                        <Route path="/" exact component={Index} />
                        <Route path="/processes/:processId/activities/:activityId" component={Activity} />
                    </div>
                </Router>
                );
    }
}

export default App;
