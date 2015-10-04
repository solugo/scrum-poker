define(['react', 'react-router', 'components/portal'], function(React, ReactRouter, Portal){

    var routes = (
        <ReactRouter.Route handler={Portal.Frame} path="/">
            <ReactRouter.DefaultRoute name="home" handler={Portal.Home} />
            <ReactRouter.Route name="login" handler={Portal.Login} />
            <ReactRouter.NotFoundRoute handler={Portal.NotFound}/>
        </ReactRouter.Route>
    );

    ReactRouter.run(routes, function (Handler) {
        React.render(<Handler/>, document.body);
    });
});