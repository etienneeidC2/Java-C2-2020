import React, { Component } from 'react';
import { Switch, Route } from "react-router-dom";
import { Menu } from 'semantic-ui-react';
import AllUsers from './AllUsers';
import UserPage from './UserPage';

class Main extends Component {
    constructor(){
        super();
        this.state = { users: [] };
    }

    componentDidMount() {

        const myHeaders = new Headers();

        myHeaders.append('Access-Control-Allow-Origin', '*');
        myHeaders.append('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, OPTIONS');
        myHeaders.append('Access-Control-Allow-Headers', '*');

        const myRequest = new Request('http://localhost:8080/users', {
            method: 'GET',
            headers: myHeaders,
            mode: 'cors',
        });

        fetch(myRequest)
            .then(response => response.json())
            .then(res => this.setState({ users: res.data }))
            .catch(err => console.log(err));
    }

    render() {
        return (
            <React.Fragment>
                <Menu inverted size='huge' borderless fixed='top'>
                    <p style={{ color: 'white' }}>The sign in with google will go somewhere here </p>
                </Menu>
                <Switch>
                    <Route
                        exact path='/'
                        render={() => <AllUsers {... this.state} />}
                    />
                    <Route
                        exact path='/:userId'
                        render={() => <UserPage />}
                    />

                </Switch>
            </React.Fragment>

        );
    }

}

export default Main;