define(['exports', 'react'], function (exports, React) {

    exports.Frame = React.createClass({
        render: function () {
            return (
                <div className="frame">
                    <div className="header"></div>
                    <div className="menu"></div>
                    <div className="body"></div>
                </div>
            );
        }
    });

})