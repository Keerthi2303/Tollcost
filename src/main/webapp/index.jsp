<%@page session="false"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="jsonsrv/brutusin-jsonsrv.js"></script>
        <script>
            var jsonsrv = brutusin["jsonsrv"].load("srv");
            jsonsrv.ajax({
                service: "jsonsrv.version",
                load: function (response, status) {
                    if (status == 200) {
                        alert("jsonsrv version: '" + response.value + "'");
                    }
                }
            });
        </script>
    </head>
    <body>
        <a href="srv?human=true">Deployed services</a>
    </body>
</html>
