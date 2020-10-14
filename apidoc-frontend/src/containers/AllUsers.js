import React, { Component } from 'react';
import { Card } from 'semantic-ui-react';

class AllUsers extends Component {

    render() {
        return (
            <React.Fragment>
                <Card.Group itemsPerRow={3} style={{ paddingTop: '55px', alignItems: 'center', justifyContent: 'center', display: 'flex' }}>
                    {this.props.users.map((user) => {
                        return (
                            <Card
                                size='huge'
                                key={user.userId}
                                href={user.userId}
                                meta={user.email}
                                header={user.firstName + ' ' + user.lastName}
                            />
                        )
                    })}
                </Card.Group>
            </React.Fragment>

        );
    }

}

export default AllUsers;