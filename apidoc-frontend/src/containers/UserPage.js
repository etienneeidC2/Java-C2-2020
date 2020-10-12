import React, { Component } from 'react';

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
                        This user hasn't added any APIs yet!!
                    </p>
                }
                {/* {this.state.apis.length &&

                } */}
            </React.Fragment>

        );
    }

}

export default UserPage;