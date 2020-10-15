import React, { Component } from 'react';
import { Input, Form, Button } from 'semantic-ui-react';

class ApiCard extends Component {

    constructor(){
        super();

        this.state = {
            apiInfo:{
                id: 'id',
                name: 'name',
                method: 'method',
                route: 'route',
                description: 'description',
                userId: 'userId'
            },
            editMode: false,
            isLoading: false
        };

        this.oldState = {};

        this.onEditClick = this.onEditClick.bind(this);
        this.onCancelClick = this.onCancelClick.bind(this);

        this.onDeleteClick = this.onDeleteClick.bind(this);
        this.onSaveClick = this.onSaveClick.bind(this);

        this.onMethodChange = this.onMethodChange.bind(this);
        this.onNameChange = this.onNameChange.bind(this);
        this.onRouteChange = this.onRouteChange.bind(this);
        this.ondDescriptionChange = this.ondDescriptionChange.bind(this);

    }

    componentDidMount(){
        this.setState({apiInfo: {... this.props}});
        
    }

    onEditClick(){
        this.oldState = {...this.state.apiInfo};
        this.setState({editMode: true});
    }

    onCancelClick(){
        this.setState({apiInfo: {... this.oldState}, editMode: false});
    }

    onDeleteClick(){
        this.props.onDelete(this.state.apiInfo.id);
    }

    onSaveClick(){
        this.props.onEdit(this.state.apiInfo);
        this.setState({editMode: false});
    }

    onMethodChange(e){
        this.setState({apiInfo: {...this.state.apiInfo, method: e.target.value} });
    }

    onNameChange(e){
        this.setState({apiInfo: {...this.state.apiInfo, name: e.target.value} });
    }

    onRouteChange(e){
        this.setState({apiInfo: {...this.state.apiInfo, route: e.target.value} });
    }

    ondDescriptionChange(e){
        this.setState({apiInfo: {...this.state.apiInfo, description: e.target.value} });
    }

    render() {

        return (
            <React.Fragment>
               {!this.state.editMode &&
                    <React.Fragment>
                        <a>{this.state.apiInfo.method + ' ' + this.state.apiInfo.name}</a>
                        <a>{this.state.apiInfo.route}</a>
                        <a>{this.state.apiInfo.description}</a>
                        {this.props.isOwner &&
                            <Button.Group>
                                <Button color='blue' content='Modifier' icon='edit' labelPosition='left' onClick={this.onEditClick}/>
                                <Button color='red' content='Supprimer' icon='trash' labelPosition='left' onClick={this.onDeleteClick}/>
                            </Button.Group>
                        }
                        
                    </React.Fragment>
                }
                {this.state.editMode &&
                    <React.Fragment>
                        <Form loading={this.state.isLoading}>
                            <Form.Field required>
                                <label>Method</label>
                                <Input placeholder='GET, POST, PUT, Delete, ...' value={this.state.apiInfo.method} onChange={this.onMethodChange}/>
                            </Form.Field>
                            <Form.Field required>
                                <label>Name</label>
                                <Input placeholder='Name' value={this.state.apiInfo.name} onChange={this.onNameChange}/>
                            </Form.Field>
                            <Form.Field required>
                                <label>Route</label>
                                <Input placeholder='Route' value={this.state.apiInfo.route} onChange={this.onRouteChange}/>
                            </Form.Field>
                            <Form.Field required>
                                <label>Description</label>
                                <Input placeholder='Description' value={this.state.apiInfo.description} onChange={this.ondDescriptionChange}/>
                            </Form.Field>
                        </Form>
                        <br/>
                        {(this.props.isOwner && this.state.editMode) &&
                        <Button.Group>
                            <Button color='green' content='Enregistrer' icon='save' labelPosition='left' onClick={this.onSaveClick}/>
                            <Button color='red' content='Annuler' icon='cancel' labelPosition='left' onClick={this.onCancelClick}/>
                        </Button.Group>
                        }
                    </React.Fragment>
                }
            </React.Fragment>

        );
    }

}

export default ApiCard;