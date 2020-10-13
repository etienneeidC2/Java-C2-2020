import React, { Component } from 'react';
import { Grid, Menu, Sidebar, Segment } from 'semantic-ui-react';
import ApiCard from './ApiCard';

class UserPage extends Component {

    state = {
        path: window.location.pathname,
        apis: []
    };

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
                                        <ApiCard
                                            name={api.name}
                                            method={api.method}
                                            route={api.route}
                                            description={api.description}
                                            userId={api.userId}
                                        />
                                    )
                                })}
                            </Grid.Column>
                        </Grid.Row>
                    </Grid>
                }
            </React.Fragment>

        );
    }

}

export default UserPage;

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