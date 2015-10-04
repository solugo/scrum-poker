define(['exports', 'jquery'], function (exports, $) {
    var jsonMime = 'application/json; charset=utf-8';

    exports.call = function (url, options) {
        var deferred = $.Deferred();

        options = $.extend({
            headers: {
                "Accept": jsonMime,
                "Content-Type": jsonMime
            }
        }, options);

        $.ajax(url, options).then(function(data, request){
            try {
                deferred.resolve(JSON.parse(data), request);
            } catch(e) {
                deferred.resolve(data, request);
            }
        }, function(request){
            try {
                deferred.reject(JSON.parse(request.responseText), request);
            } catch(e) {
                deferred.reject(request.responseText, request);
            }
        });

        return deferred.promise();
    };

    exports.get = function(url, options) {
        return exports.call(url, $.extend({
            method: "GET"
        }, options));
    };

    exports.post = function(url, data, options) {
        return exports.call(url, $.extend({
            method: "POST",
            data: JSON.stringify(data)
        }, options));
    };
});