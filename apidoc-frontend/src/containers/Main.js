import React, { Component } from 'react';
import { Switch, Route } from "react-router-dom";
import { Menu } from 'semantic-ui-react';
import AllUsers from './AllUsers';
import UserPage from './UserPage';

import { GoogleLogin } from 'react-google-login';

class Main extends Component {
    constructor() {
        super();
        this.state = { users: [], userId: '', userToken: '' };

        this.onSuccess = this.onSuccess.bind(this);
        this.onError = this.onError.bind(this);
    }

    componentDidMount() {

        this.loadGoogleAuthApi();

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

    loadGoogleAuthApi() {
        try {
            window.gapi.load('auth2', () => {
                window.gapi.auth2.init({
                    client_id: '637286620526-dtk2eugaq7ndu8k7kcigogpq609r6fcg.apps.googleusercontent.com'
                }).then(() => {
                    window.gapi.signin2.render(
                        'google-sign-in-button',
                        {
                            width: 200,
                            height: 40,
                            longtitle: true,
                            onsuccess: this.onSuccess,
                        });
                })
            }
            );
        } catch (err) {
            console.log(err);
        }
    }

    onSuccess(googleUser) {
        // let self = this;
        // self.providerData = {
        // 	provider_token: googleUser.getAuthResponse().id_token,
        // 	provider: 'google',
        // }
        // self.afterSuccess();

        const myHeaders = new Headers();

        myHeaders.append('Access-Control-Allow-Origin', '*');
        myHeaders.append('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, OPTIONS');
        myHeaders.append('Access-Control-Allow-Headers', '*');
        myHeaders.append('Content-Type', 'application/json');

        const myRequest = new Request('http://localhost:8080/login', {
            method: 'POST',
            headers: myHeaders,
            mode: 'cors',
            body: JSON.stringify({ token: googleUser.getAuthResponse().id_token })
        });

        fetch(myRequest)
            .then(response => response.json())
            .then(res => this.setState({ userId: res.data.userId, userToken: res.data.token }))
            .catch(err => console.log(err));
    }

    onError(googleError) {
        console.log(googleError)
    }

    render() {
        return (
            <React.Fragment>
                <Menu inverted size='huge' borderless fixed='top'>
                    <GoogleLogin
                        clientId="637286620526-dtk2eugaq7ndu8k7kcigogpq609r6fcg.apps.googleusercontent.com"
                        buttonText="Continue with google"
                        onSuccess={this.onSuccess}
                        onFailure={this.onError}
                        cookiePolicy={'single_host_origin'}
                    />
                </Menu>
                <Switch>
                    <Route
                        exact path='/'
                        render={() => <AllUsers {...{ users: this.state.users }} />}
                    />
                    <Route
                        exact path='/:userId'
                        render={() => {
                            return (
                                <UserPage {...{ userId: this.state.userId, userToken: this.state.userToken }} />
                            )
                        }}
                    />

                </Switch>
            </React.Fragment>
        );
    }

}

export default Main;