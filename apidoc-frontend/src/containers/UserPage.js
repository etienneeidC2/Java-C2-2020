import React, { Component } from 'react';
import { Grid, Menu, Sidebar, Form, Input, Button, TextArea } from 'semantic-ui-react';
import ApiCard from './ApiCard';

class UserPage extends Component {

    constructor(props) {
        super(props);
        this.initApiInfo = {
            id: '',
            name: '',
            method: '',
            route: '',
            description: '',
            userId: ''
        };
        this.state = {
            addMode: false,
            renderAdd: true,
            isOwner: (window.location.pathname === `/${this.props.userId}`) ? true : false,
            path: window.location.pathname,
            apiInfo: this.initApiInfo,
            apis: []
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

    componentDidUpdate(prevProps) {
        if (this.props.userId != prevProps.userId) {
            this.setState({ isOwner: (window.location.pathname === `/${this.props.userId}`) ? true : false })
        }
    }

    onAddClick() {
        this.setState({ addMode: true, renderAdd: false });
    }

    onCancelClick() {
        this.setState({ addMode: false, renderAdd: true });
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
        myHeaders.append('Authorization', `Bearer ${this.props.userToken}`);
        myHeaders.append('Content-Type', 'application/json');

        const myRequest = new Request(`http://localhost:8080/api`, {
            method: 'POST',
            headers: myHeaders,
            mode: 'cors',
            body: JSON.stringify(this.state.apiInfo)
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
        myHeaders.append('Authorization', `Bearer ${this.props.userToken}`);
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
        myHeaders.append('Authorization', `Bearer ${this.props.userToken}`);

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

        console.log('addMode', this.state.addMode)
        console.log('apis.length', this.state.apis.length)
        console.log('isOwner', this.state.isOwner)

        return (
            <React.Fragment>
                {(!this.state.apis.length && !this.state.isOwner) &&
                    <p style={{ color: 'black', paddingTop: '150px', alignItems: 'center', justifyContent: 'center', display: 'flex' }}>
                        Cet utilisateur n'a pas encore ajouté d'API !!
                    </p>
                }
                {(!this.state.apis.length && this.state.isOwner && this.state.renderAdd) &&
                    <Button style={{ top: '48px' }} color='green' content='Ajouter Une API' icon='save' labelPosition='left' onClick={this.onAddClick} />
                }
                {this.state.apis.length > 0 &&
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
                                        <React.Fragment key={api.id}>
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
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                }
                {(this.state.isOwner && this.state.addMode) &&
                    <Grid columns={3} style={{ paddingTop: '55px' }}>
                        <Grid.Row stretched>
                            <Grid.Column />
                            <Grid.Column>
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
                                        <TextArea placeholder='Description' onChange={this.ondDescriptionChange} />
                                    </Form.Field>
                                </Form>
                                <br />
                                {(this.state.isOwner && this.state.addMode) &&
                                    <Button.Group>
                                        <Button color='green' content='Ajouter' icon='save' labelPosition='left' onClick={this.onAdd} />
                                        <Button color='red' content='Annuler' icon='cancel' labelPosition='left' onClick={this.onCancelClick} />
                                    </Button.Group>
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