'use strict';

/* =======================================================================
                    Autocomplete search bar API
========================================================================*/



$(function() {
    function log( message ) {
        $( "<div>" ).text( message ).prependTo( "#log" );
        $( "#log" ).scrollTop( 0 );
    }
    $( "#origin" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "https://api.sandbox.amadeus.com/v1.2/airports/autocomplete",
                dataType: "json",
                data: {
                    apikey: "TXIA8oaPoUrPTKogMt0y496orBf38IqM",
                    term: request.term
                },
                success: function( data ) {
                    response( data );
                }
            });
        },
        minLength: 3,
        select: function( event, ui ) {
            log( ui.item ?
                "Selected: " + ui.item.label :
                "Nothing selected, input was " + this.value);
        },
        open: function() {
            $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
        },
        close: function() {
            $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
        }
    });
});







/* =======================================================================
                    Low flight API
========================================================================*/


$(document).on('click', function () {

    var origin = "&origin=" + $("#origin").val();

    var destination = "&destination=" + $("#destination").val();

    var departure_date = "&departure_date=" + $("#departure").val();

    var maximumPrice = "&max_price=" + $("#price").val();

    var currency = "&currency=" + $("#currency").val();

    var api_key = 'TXIA8oaPoUrPTKogMt0y496orBf38IqM';

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
            // console.log(response.results[r]);

            // $("#solo").html("<div class='card-body text-center'>" + response.results[r] + "\xB0" + "</div>");
        }
    }

    main();

});




/* =======================================================================
                    POI Map
========================================================================*/

$.get('https://api.sandbox.amadeus.com/v1.2/points-of-interest/yapq-search-circle?', {
    dataType : 'json',
    apikey: "v6aAEJ4TnGgvIJhW6cKrX7wW0hCVwsfy",
    latitude:'41.878',
    longitude:'-87.645',
    radius:'1',
    category:'museum'
}).done(function(data){
    for(var i=0;i<data.points_of_interest.length;i++)
        console.log(data.points_of_interest[i]);
});





