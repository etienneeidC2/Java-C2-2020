import React, { Component } from 'react';
import { Input, Form, Button } from 'semantic-ui-react';

class ApiCard extends Component {

    state = {
        apiInfo:{
            name: 'name',
            method: 'method',
            route: 'route',
            description: 'description',
            userId: 'userId'
        },
        editMode: false,
        isOwner: true,
        isLoading: false
    };

    componentDidMount(){
        this.setState({apiInfo: {... this.props}});
    }

    render() {

        return (
            <React.Fragment>
               {!this.state.editMode &&
                    <React.Fragment>
                        <a>{this.state.apiInfo.method + ' ' + this.state.apiInfo.name}</a>
                        <a>{this.state.apiInfo.route}</a>
                        <a>{this.state.apiInfo.description}</a>
                        {this.state.isOwner &&
                            <React.Fragment>
                                <Button content='Edit' icon='edit' labelPosition='left'/>
                                <Button content='Delete' icon='trash' labelPosition='left' negative/>
                            </React.Fragment>
                        }
                        
                    </React.Fragment>
                }
                {this.state.editMode &&
                    <React.Fragment>
                        <Form loading={this.state.isLoading}>
                            <Form.Field required>
                                <label>Method</label>
                                <Input placeholder='GET, POST, PUT, Delete, ...' />
                            </Form.Field>
                            <Form.Field required>
                                <label>Name</label>
                                <Input placeholder='Name' />
                            </Form.Field>
                            <Form.Field required>
                                <label>Route</label>
                                <Input placeholder='Route' />
                            </Form.Field>
                            <Form.Field required>
                                <label>Description</label>
                                <Input placeholder='Description' />
                            </Form.Field>
                        </Form>
                        <br/>
                        {(this.state.isOwner && this.state.editMode) &&
                           <Button content='Cancel' icon='cancel' labelPosition='left' negative/>
                        }
                    </React.Fragment>
                }
            </React.Fragment>

        );
    }

}

export default ApiCard;

/**
 * <
Grid columns={2}>
                        <Grid.Column>
                            <Sidebar.Pushable as={Segment}>

                            <Sidebar.Pusher>
                                <Segment basic>
                                <Header as='h3'>Application Content</Header>
                                <Image src='https://react.semantic-ui.com/images/wireframe/paragraph.png' />
                                </Segment>
                            </Sidebar.Pusher>
                            </Sidebar.Pushable>
                        </Grid.Column>
                        </Grid>
 */