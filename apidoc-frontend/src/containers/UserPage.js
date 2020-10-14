import React, { Component } from 'react';
import { Grid, Menu, Sidebar, Form, Input, Button } from 'semantic-ui-react';
import ApiCard from './ApiCard';

class UserPage extends Component {

    constructor() {
        super();
        this.initApiInfo = {
            id: 'id',
            name: 'name',
            method: 'method',
            route: 'route',
            description: 'description',
            userId: 'userId'
        };
        this.state = {
            addMode: false,
            isOwner: true,
            path: window.location.pathname,
            apiInfo: this.initApiInfo,
            apis: [
                // to be removed later
                // {
                //     id: 1,
                //     method: 'GET',
                //     name: 'some functionality',
                //     route: 'http://localhost:8080/api/func',
                //     description: 'some kind of description',
                //     userId: 'x',
                // },
                // {
                //     id: 2,
                //     method: 'Post',
                //     name: 'some other functionality',
                //     route: 'http://localhost:8080/api/dunc',
                //     description: 'some other kind of description',
                //     userId: 'x',
                // },
                // {
                //     id: 3,
                //     method: 'PUT',
                //     name: 'some other other functionality',
                //     route: 'http://localhost:8080/api/punc',
                //     description: 'some other other kind of description',
                //     userId: 'x',
                // }
            ]
        };

        this.onMethodChange = this.onMethodChange.bind(this);
        this.onNameChange = this.onNameChange.bind(this);
        this.onRouteChange = this.onRouteChange.bind(this);
        this.ondDescriptionChange = this.ondDescriptionChange.bind(this);

        this.onAdd = this.onAdd.bind(this);
        this.onCancelClick = this.onCancelClick.bind(this);
        this.onAddClick = this.onAddClick.bind(this);
        this.onEdit = this.onEdit.bind(this);
        this.onDelete = this.onDelete.bind(this);
    }


    componentDidMount() {

        const myHeaders = new Headers();

        myHeaders.append('Access-Control-Allow-Origin', '*');
        myHeaders.append('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, OPTIONS');
        myHeaders.append('Access-Control-Allow-Headers', '*');

        const myRequest = new Request(`http://localhost:8080/api${this.state.path}`, {
            method: 'GET',
            headers: myHeaders,
            mode: 'cors',
        });

        fetch(myRequest)
            .then(response => response.json())
            .then(res => this.setState({ apis: res.data }))
            .catch(err => console.log(err));
    }

    onAddClick() {
        this.setState({ addMode: true });
    }

    onCancelClick() {
        this.setState({ addMode: false });
    }

    onMethodChange(e) {
        this.setState({ apiInfo: { ...this.state.apiInfo, method: e.target.value } });
    }

    onNameChange(e) {
        this.setState({ apiInfo: { ...this.state.apiInfo, name: e.target.value } });
    }

    onRouteChange(e) {
        this.setState({ apiInfo: { ...this.state.apiInfo, route: e.target.value } });
    }

    ondDescriptionChange(e) {
        this.setState({ apiInfo: { ...this.state.apiInfo, description: e.target.value } });
    }

    onAdd() {
        const myHeaders = new Headers();

        myHeaders.append('Access-Control-Allow-Origin', '*');
        myHeaders.append('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, OPTIONS');
        myHeaders.append('Access-Control-Allow-Headers', '*');
        myHeaders.append('Content-Type', 'application/json');

        const myRequest = new Request(`http://localhost:8080/api`, {
            method: 'POST',
            headers: myHeaders,
            mode: 'cors',
            body: this.state.apiInfo
        });

        fetch(myRequest)
            .then(response => response.json())
            .then(res => {
                console.log(res);
                if (res.status === 'success') {
                    let updated = [... this.state.apis, res.data];
                    this.setState({ apis: [...updated], addMode: false, apiInfo: this.initApiInfo });
                }
            })
            .catch(err => console.log(err));
    }

    onEdit(data) {
        const myHeaders = new Headers();

        myHeaders.append('Access-Control-Allow-Origin', '*');
        myHeaders.append('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, OPTIONS');
        myHeaders.append('Access-Control-Allow-Headers', '*');
        myHeaders.append('Content-Type', 'application/json');

        const myRequest = new Request(`http://localhost:8080/api/${data.id}`, {
            method: 'PUT',
            headers: myHeaders,
            mode: 'cors',
            body: JSON.stringify(data)
        });

        fetch(myRequest)
            .then(response => response.json())
            .then(res => {
                console.log(res);
                if (res.status === 'success') {
                    let updated = this.state.apis.map((api) => {
                        if (api.id != data.id) {
                            return api;
                        } else {
                            return res.data;
                        }
                    });
                    this.setState({ apis: [...updated] });
                }
            })
            .catch(err => console.log(err));
    }

    onDelete(id) {

        const myHeaders = new Headers();

        myHeaders.append('Access-Control-Allow-Origin', '*');
        myHeaders.append('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, OPTIONS');
        myHeaders.append('Access-Control-Allow-Headers', '*');

        const myRequest = new Request(`http://localhost:8080/api/${id}`, {
            method: 'DELETE',
            headers: myHeaders,
            mode: 'cors',
        });

        fetch(myRequest)
            .then(response => response.json())
            .then(res => {
                console.log(res);
                if (res.status === 'success') {
                    let filtered = this.state.apis.filter((api) => { return api.id != id });
                    this.setState({ apis: [...filtered] });
                }
            })
            .catch(err => console.log(err));
    }

    render() {

        return (
            <React.Fragment>
                {!this.state.apis.length &&
                    <p style={{ color: 'black', paddingTop: '150px', alignItems: 'center', justifyContent: 'center', display: 'flex' }}>
                        Cet utilisateur n'a pas encore ajouté d'API !!
                    </p>
                }
                {this.state.apis.length &&
                    <Grid columns={3} style={{ paddingTop: '55px' }}>
                        <Grid.Row stretched>
                            <Grid.Column>
                                <Sidebar style={{ top: '48px' }} as={Menu} borderless inverted vertical visible width="wide">
                                    {this.state.apis.map((api) => {
                                        return (
                                            <Menu.Item key={api.id} as='a'>
                                                {api.method + ' ' + api.name}
                                            </Menu.Item>
                                        )
                                    })}
                                </Sidebar>
                            </Grid.Column>
                            <Grid.Column>
                                {this.state.apis.map((api) => {
                                    return (
                                        <React.Fragment>
                                            <ApiCard
                                                id={parseInt(api.id, 10)}
                                                name={api.name}
                                                method={api.method}
                                                route={api.route}
                                                description={api.description}
                                                userId={api.userId}
                                                onEdit={this.onEdit}
                                                onDelete={this.onDelete}
                                                isOwner={this.state.isOwner}
                                            />
                                            <br />
                                        </React.Fragment>
                                    )
                                })}
                                {(this.state.isOwner && !this.state.addMode) &&
                                    <Button color='green' content='Ajouter Une API' icon='save' labelPosition='left' onClick={this.onAddClick} />
                                }
                                {(this.state.isOwner && this.state.addMode) &&
                                    <React.Fragment>
                                        <Form loading={this.state.isLoading}>
                                            <Form.Field required>
                                                <label>Method</label>
                                                <Input placeholder='GET, POST, PUT, Delete, ...' onChange={this.onMethodChange} />
                                            </Form.Field>
                                            <Form.Field required>
                                                <label>Name</label>
                                                <Input placeholder='Name' onChange={this.onNameChange} />
                                            </Form.Field>
                                            <Form.Field required>
                                                <label>Route</label>
                                                <Input placeholder='Route' onChange={this.onRouteChange} />
                                            </Form.Field>
                                            <Form.Field required>
                                                <label>Description</label>
                                                <Input placeholder='Description' onChange={this.ondDescriptionChange} />
                                            </Form.Field>
                                        </Form>
                                        <br />
                                        {(this.state.isOwner && this.state.addMode) &&
                                            <Button.Group>
                                                <Button color='green' content='Ajouter' icon='save' labelPosition='left' onClick={this.onAdd} />
                                                <Button color='red' content='Annuler' icon='cancel' labelPosition='left' onClick={this.onCancelClick} />
                                            </Button.Group>
                                        }
                                    </React.Fragment>
                                }
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                }
            </React.Fragment>

        );
    }

}

export default UserPage;