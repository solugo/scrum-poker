define(['exports', 'react'], function (exports, React) {

    exports.Form = React.createClass({
        render: function () {
            return (
                <form onSubmit={this.props.onSubmit}>
                    {this.props.children}
                </form>
            );
        }
    });

    exports.FormField = React.createClass({
        render: function () {
            return (
                <div className="form-group">
                    <label className="control-label">{this.props.title}</label>

                    <div>
                        {this.props.children}
                    </div>
                </div>
            );
        }
    });

    exports.TabPane = React.createClass({
        getInitialState: function () {
            return {
                selected: 0
            }
        },
        render: function () {
            var self = this;
            return (
                <div className="col-xs-12">
                    <ul className={'nav nav-tabs' + (this.props.justified ? ' justified' : '')}>
                        {
                            self.props.children.map(function (child, index) {
                                var selectFunction = function () {
                                    self.setState({selected: index});
                                };
                                return (
                                    <li key={index} role="presentation"
                                        className={(index === self.state.selected && 'active')}>
                                        <a onClick={selectFunction}>{child.props.title}</a>
                                    </li>
                                );
                            })
                        }
                    </ul>
                    <div className="row" style={{paddingTop: '20px'}}>
                        <div className="col-xs-12">
                            {
                                self.props.children.map(function (child, index) {
                                    return index === self.state.selected && child;
                                })
                            }
                        </div>
                        <div className="clearfix visible-xs-block"></div>
                    </div>
                </div>
            );
        }
    });

    exports.Button = React.createClass({
        render: function () {
            var cx = React.addons.classSet;
            var classes = {};
            classes['btn'] = true;
            classes['btn-primary'] = this.props.type === 'submit';
            classes['btn-' + this.props.type] = this.props.type && this.props.type !== 'submit';

            var type = (this.props.type === 'submit' ? 'submit' : 'button');
            return (
                <div className="input-group col-xs-12">
                    <button type={type} className={cx(classes)} onClick={this.props.onClick}
                            disabled={this.props.disabled}>
                        {this.props.text}
                    </button>
                </div>
            );
        }
    });

    exports.ButtonGroup = React.createClass({
        render: function () {
            return (
                <div className="btn-group pull-right" role="group">
                    {this.props.children}
                </div>
            );
        }
    });

    exports.TextInput = React.createClass({
        getInitialState: function () {
            return {
                value: this.props.value
            }
        },
        render: function () {
            return (
                <div className="input-group col-xs-12">
                    <input type="text" className="form-control" placeholder={this.props.placeholder}
                           value={this.state.value} valueLink={this.props.valueLink}/>
                </div>
            );
        }
    });

    exports.NumberInput = React.createClass({
        getInitialState: function () {
            return {
                value: this.props.value
            }
        },
        render: function () {
            return (
                <div className="input-group col-xs-12">
                    <input type="number" className="form-control" placeholder={this.props.placeholder}
                           value={this.state.value} valueLink={this.props.valueLink}/>
                </div>
            );
        }
    });

    exports.PasswordInput = React.createClass({
        getInitialState: function () {
            return {
                value: this.props.value,
                visible: false
            }
        },
        handleSwitch: function () {
            this.setState({visible: !this.state.visible})
        },
        render: function () {
            return (
                <div className="input-group col-xs-12">
                    <input type={this.state.visible ? 'text' : 'password'} className="form-control"
                           placeholder={this.props.placeholder}
                           value={this.state.value} valueLink={this.props.valueLink}/>
                    <span className="input-group-btn">
                        <button className="btn btn-default" type="button" onClick={this.handleSwitch}>
                            <i className={'glyphicon ' + (this.state.visible ? 'glyphicon-eye-open' : 'glyphicon-eye-close')}/>
                        </button>
                    </span>
                </div>
            );
        }
    });

})