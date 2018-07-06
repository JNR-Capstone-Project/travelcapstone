'use strict';



/* =======================================================================
                    Form validation
========================================================================*/




window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
        form.addEventListener('submit', function(event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
}, false);


/* =======================================================================
                    Autocomplete search bar API
========================================================================*/


$(function() {
    function log( message ) {
        $( "<div>" ).text( message ).prependTo( "#log" );
        $( "#log" ).scrollTop( 0 );
    }
    $( ".airports" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "https://api.sandbox.amadeus.com/v1.2/airports/autocomplete",
                dataType: "json",
                data: {


                    apikey: "",


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

var api_key = '';


$(function () {



        document.getElementById("lowFlight").addEventListener('click', function () {


            var origin = "&origin=" + $("#origin").val();

            var destination = "&destination=" + $("#destination").val();

            var departure_date = "&departure_date=" + $("#departure").val();

            var return_date = "&return_date=" + $("#returnDate").val();

            var maximumPrice = "&max_price=" + $("#price").val();

            var currency = "&currency=USD" ;





            // if(return_date.val() == null) {
            //     var resource_url = 'https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=' + api_key + origin + destination + departure_date + maximumPrice + currency;
            // } else {
               var resource_url = 'https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=' + api_key + origin + destination + departure_date + return_date + maximumPrice + currency;

            // }

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

});



/* =======================================================================
                    Hotel Api
========================================================================*/




$(function () {
    document.getElementById("cheapHotel").addEventListener('click', function () {

        var location = "&location=" + $("#hotelDestination").val();
        var checkIn = "&check_in=" + $("#checkIn").val();
        var checkOut = "&check_out=" + $("#checkOut").val();

        var url_hotel = "https://api.sandbox.amadeus.com/v1.2/hotels/search-airport?apikey=" + api_key + location + checkIn + checkOut;


        var request = $.get(url_hotel);
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

            for(var i = 0; i <= response.results.length; i++){
                console.log(response.results[i].property_name);
                $('.hotel-card').append(
                    "<h2 class='hotel-car-des'>" + response.results[i].property_name + "\xB0" +
                    "</h2>" + "<p class='block'>" + response.results[i].contacts[0].detail + "\xB0" +
                    "</p><br/><p class='block'>" + response.results[i].contacts[1].detail + "\xB0" +
                    "</p><br/><p class='block'>" + response.results[i].contacts[2].detail + "\xB0" +   "</p>");




            }








        }

        main();





    });
});

/* =======================================================================
                    car rental
========================================================================*/

$(function () {
    document.getElementById("carRental").addEventListener('click', function () {

        var loc = "&location=" + $("#rentalLocation").val();
        var pickup = "&pick_up=" + $("#pickUp").val();
        var dropoff = "&drop_off=" + $("#dropOff").val();

        var url_rental = "https://api.sandbox.amadeus.com/v1.2/cars/search-airport?apikey=" + api_key + loc + pickup + dropoff;

        var request = $.get(url_rental);
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

        }

        main();



    });
});



/* =======================================================================
                    POI Map
========================================================================*/



$(function () {

    document.getElementById("ent").addEventListener('click', function () {



//building the map

// var location={ lat: 19.775221,
//     lng: -99.885829}



        var mapOptions = {
            zoom: 12,
            center: {
                lat: 42.343794,
                lng:-71.067170
            },
            mapTypeId: google.maps.MapTypeId.TERRAIN,
            draggable: true
        };
        var mapCanvas = document.getElementById('poiMap');
        var map = new google.maps.Map(mapCanvas, mapOptions);


//setting the main marker ...should be the user destination






        function setMarker(location) {
            marker.setPosition(location);
        }

        new google.maps.Marker({
            position: {
                lat: 42.3437941,
                lng:-71.067170
            },
            map: map
        });


        function setMarker(location) {
            marker.setPosition(location);

        }




// function to get a marker for each poi on the JSON
        function poiToMarker( data)
        {
            for (var i = 0; i < data.points_of_interest.length; i++) {
                new google.maps.Marker({
                    position: {
                        lat: data.points_of_interest[i].location.latitude,
                        lng: data.points_of_interest[i].location.longitude
                    },
                    map: map
                });
                $("#poiPosts").append(createReport(data.points_of_interest[i]));

            }


        }


// obtain the Poi JSON  passing a location and setting the markers for the destination


        $.get('https://api.sandbox.amadeus.com/v1.2/points-of-interest/yapq-search-circle?',
            {
                dataType: 'json',
                apikey:'',
                latitude:  42.343794,
                longitude:  -71.067170,
                radius: '20'
            }).done(function (data) {
            console.log(data);
            poiToMarker(data);
        });


//creating the div for the view

        function createReport(poi) {
            var htmlPlace="";
            htmlPlace+="<div class='screen'>";
            htmlPlace+="<h3>"+poi.title+"</h3>"+"\n";
            htmlPlace+="<img src="+poi.main_image+">"+"\n";
            htmlPlace+="<p> "+poi.details.description+"</p>"+"\n";
            htmlPlace+="</div>";
            return htmlPlace
        }



    });

});


/* =======================================================================
                    Airport Hotel Map
========================================================================*/

function airportHotel(location, check_in,check_out) {


    $.get('https://api.sandbox.amadeus.com/v1.2/hotels/search-airport',
        {
            dataType: 'json',
            apikey: '',
            check_in: check_in,
            check_out: check_out,
            location: location
        }).done(function (data) {
        console.log(data.results);
        // $(".container").html(" "); line to clean the results every time for a new search load
        for (var i =0;i< data.results.length; i++)
        {
            $("#hotels").append(setHotel(data.results[i]));

        }
    })
}



airportHotel('BOS','2018-12-15','2018-12-16')



//row struture for the hotel
var esta="type";

function setHotel(hotel)
{
    var htmlHotel="";
    htmlHotel+="<tr>";
    htmlHotel+="<td>"+hotel.property_name+"</td>";
    htmlHotel+="<td>"+hotel.address.line1+"</td>";
    htmlHotel+="<td>"+hotel.contacts[0].type+": "+hotel.contacts[0].detail+"</td>";
    htmlHotel+="</tr>";
    return htmlHotel;

}







