define(['exports', 'rest', 'react', 'react-router', 'components/controls'], function (exports, Rest, React, ReactRouter, Controls) {

    exports.Frame = React.createClass({
        render: function () {
            return (
                <div id="frame" className="container">
                    <div id="header">
                    </div>
                    <div id="body">
                        <ReactRouter.RouteHandler />
                    </div>
                </div>
            );
        }
    });

    exports.NotFound = React.createClass({
        render: function () {
            return (
                <div>Page not found</div>
            );
        }
    });

    exports.Home = React.createClass({
        mixins: [React.addons.LinkedStateMixin],
        getInitialState: function () {
            return {}
        },
        handleCreate: function () {

        },
        canJoin: function() {
            return this.state.name && this.state.game;
        },
        handleJoin: function () {
            debugger;
            Rest.post('/rest/accounts/transient', {
                name: this.state.name
            }).then(function (loginInfo) {
                console.log(loginInfo);
            });
        },
        render: function () {
            return (
                <Controls.TabPane>
                    <Controls.Form title="An Spiel teilnehmen" onSubmit={this.handleJoin}>
                        <Controls.FormField title="Name">
                            <Controls.TextInput valueLink={this.linkState('name')}/>
                        </Controls.FormField>
                        <Controls.FormField title="Spiel">
                            <Controls.NumberInput valueLink={this.linkState('game')}/>
                        </Controls.FormField>
                        <Controls.ButtonGroup>
                            <Controls.Button disabled={!this.canJoin()} text="Teilmehmen" type='submit'/>
                        </Controls.ButtonGroup>
                    </Controls.Form>
                    <Controls.Form title="Spiel erstellen" onSubmit={this.handleCreate}>
                        <Controls.FormField title="Titel">
                            <Controls.TextInput valueLink={this.linkState('email')}/>
                        </Controls.FormField>
                        <Controls.ButtonGroup>
                            <Controls.Button text="Erstellen" type='submit'/>
                        </Controls.ButtonGroup>
                    </Controls.Form>
                </Controls.TabPane>
            );
        }
    });

    exports.Login = React.createClass({
        mixins: [React.addons.LinkedStateMixin],
        getInitialState: function () {
            return {}
        },
        handleLogin: function () {
        },
        render: function () {
            return (
                <Controls.TabPane>
                    <Controls.Form title="Login" onSubmit={this.handleSubmit}>
                        <Controls.FormField title="Email">
                            <Controls.TextInput valueLink={this.linkState('email')}/>
                        </Controls.FormField>
                        <Controls.FormField title="Passwort">
                            <Controls.PasswordInput valueLink={this.linkState('password')}/>
                        </Controls.FormField>
                        <Controls.ButtonGroup>
                            <Controls.Button text="Login" type='primary' onClick={this.handleLogin}/>
                        </Controls.ButtonGroup>
                    </Controls.Form>
                    <Controls.Form title="Register">
                    </Controls.Form>
                </Controls.TabPane>
            );
        }
    });

})