import React from 'react';
import { Route, BrowserRouter, withRouter, Switch } from "react-router-dom";
import Main from './containers/Main';
import './App.css';

function App() {
	return (
		<div className="App">
			<BrowserRouter>
				<Switch>
					<Route path="/" component={withRouter(Main)} />
				</Switch>
			</BrowserRouter>
		</div>
	);
}

export default App;
