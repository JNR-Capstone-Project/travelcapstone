'use strict';

/* =======================================================================
                    Autocomplete search bar API
========================================================================*/









/* =======================================================================
                    Low flight API
========================================================================*/


$(document).on('click', function () {

    var origin = "&origin=" + $("#origin").val();

    var destination = "&destination=" + $("#destination").val();

    var departure_date = "&departure_date=" + $("#departure").val();

    var maximumPrice = "&max_price=" + $("#price").val();

    var currency = "&currency=" + $("#currency").val();

    // var api_key = '';

    var resource_url = 'https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=' + api_key + origin + destination + departure_date + maximumPrice + currency;


    var request=$.get(resource_url);
    request.fail(function (current, status, error) {
        console.log(status);
        console.log(error);
    });

    console.log(request);
    request.done(function (response) {
        //Since we are calling data out of the same array we can create a loop to put the machine to work
        main(response);
    });

    function main(response) {

        console.log(response);
        //this loop will iterate through the layers of data
        for (var r = 0; r < 50; r++){
            console.log(response.results[r]);

            // $("#solo").html("<div class='card-body text-center'>" + response.results[r] + "\xB0" + "</div>");




        }
    }

    main();

});







