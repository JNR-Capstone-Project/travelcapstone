'use strict';

var locationAirport;
var airportCode;
const apiKey = document.getElementById("amadeus-API").value;

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


                    apikey: apiKey,



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


$(function () {


        document.getElementById("lowFlight").addEventListener('click', function () {


            var origin = "&origin=" + $("#origin").val();
            var destination = "&destination=" + $("#destination").val();
            var departure_date = "&departure_date=" + $("#departure").val();
            var return_date = "&return_date=" + $("#returnDate").val();
            var maximumPrice = "&max_price=" + $("#price").val();
            var currency = "&currency=USD";
            airportCode=$("#destination").val();
            var resource_url = 'https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?apikey=' + apiKey + origin + destination + departure_date + return_date + maximumPrice + currency;
            var airportLocationUrl='https://api.sandbox.amadeus.com/v1.2/location/'+$("#destination").val()+'?apikey='+apiKey;

            var request=$.get(resource_url);
            request.fail(function (current, status, error) {
                console.log(status);
                console.log(error);
            });

            var airportLocation=$.get(airportLocationUrl);
            airportLocation.done(function (response) {
                   locationAirport=response.airports[0].location;
                   console.log(locationAirport);
            });

            request.done(function (response) {
                console.log(response);

               for (var i=0;i<response.results.length;i++){
                   $('#flight').append(setFly(response.results[i]));


               }


            });

            function setFly(fly) {
                var htmlFly = "";
                var trim = fly.itineraries[0].outbound.flights[0].departs_at.slice(11,16);
                var xtrim = fly.itineraries[0].inbound.flights[0].departs_at.slice(11,16);
                var airline = fly.itineraries[0].outbound.flights[0].marketing_airline;


// fetch
                var hours = Number(trim.split(':')[0]);
                var minutes = Number(trim.split(':')[1]);

                var xhours = Number(xtrim.split(':')[0]);
                var xminutes = Number(xtrim.split(':')[1]);

// calculate
                var timeValue;
                var timeV;

                if (hours  > 0 && hours  <= 12) {
                    timeValue= "" + hours;

                } else if (hours  > 12) {
                    timeValue= "" + (hours - 12);

                } else if (hours == 0) {
                    timeValue= "12";

                }

                if (xhours > 0 && xhours <= 12) {
                    timeV = "" + xhours;
                } else if ( xhours > 12) {
                    timeV = "" + (xhours - 12)
                } else if (xhours == 0) {
                    timeV = "12";
                }


                timeValue += (minutes < 10) ? ":0" + minutes : ":" + minutes;  // get minutes
                timeValue += (hours >= 12) ? " P.M" : " A.M";  // get AM/PM

                timeV += (xminutes < 10) ? ":0" + xminutes : ":" + xminutes;
                timeV += (xhours >= 12) ? " P.M" : " A.M";  // get AM/PM


                htmlFly += "<tr>";
                htmlFly += "<td>" + fly.itineraries[0].outbound.flights[0].flight_number + "</td>";
                htmlFly += "<td>" + timeValue + "</td>";
                htmlFly += "<td>" + fly.itineraries[0].outbound.duration + "</td>";
                htmlFly += "<td>" + fly.itineraries[0].inbound.flights[0].flight_number + "</td>";
                htmlFly += "<td>" + timeV + "</td>";
                htmlFly += "<td>" + fly.itineraries[0].inbound.duration + "</td>";
                htmlFly += "<td>$" + fly.fare.total_price + "</td>";
                if(airline === 'AA'){

                    htmlFly += "<td><a href='https://www.aa.com/homePage.do' ><img src='https://vignette.wikia.nocookie.net/logopedia/images/d/d7/American_Airlines_logo.svg/revision/latest/scale-to-width-down/340?cb=20130728031212' alt='none' class='logo'/>" + "</a></td>";
                }
                if(airline === 'UA'){
                    htmlFly += "<td><a href='https://www.united.com/ual/en/us/'><img src='https://vignette.wikia.nocookie.net/logopedia/images/0/0a/United_Airlines_2010.svg/revision/latest/scale-to-width-down/250?cb=20180505152143' alt='none' class='logo'/>"  + "</a></td>";

                }
                if (airline === 'DL'){
                    htmlFly += "<td><a href='https://www.delta.com/flight-search/book-a-flight'><img src='https://vignette.wikia.nocookie.net/logopedia/images/c/cc/DLCON.gif/revision/latest/thumbnail-down/width/98/height/20?cb=20101015204631' alt='none' class='logo'/>" + "</a></td>";

                }



                htmlFly += "</tr>";
                return htmlFly;

            }


        // <th><i class="fa fa-building-o"></i>Departure</th>
        //     <th><i class="icon_pin_alt"></i>Duration</th>
        //     <th><i class="fa fa-money"></i> Return</th>
        //     <th><i class="fa fa-money"></i> Duration</th>
        //     <th><i class="icon_mobile"></i> Price</th>
        //     <th><i class="icon_tool"></i> Book with Airline</th>

        });

});


/* =======================================================================
                    Hotel Api
========================================================================*/




/* =======================================================================
                    Airport Hotel Map
========================================================================*/

$(function () {

    document.getElementById("cheapHotel").addEventListener('click', function () {


        var location = airportCode;
        console.log(airportCode);
        var checkIn = $("#checkIn").val();
        console.log(checkIn);
        var checkOut = $("#checkOut").val();
        console.log(checkOut);


        function airportHotel(location, check_in, check_out) {

            $.get('https://api.sandbox.amadeus.com/v1.2/hotels/search-airport',
                {
                    dataType: 'json',
                    apikey: apiKey,
                    check_in: check_in,
                    check_out: check_out,
                    location: location
                }).done(function (data) {
                console.log(data.results);
                // $(".container").html(" "); line to clean the results every time for a new search load
                for (var i = 0; i < data.results.length; i++) {
                    $("#hotels").append(setHotel(data.results[i]));

                }
            });
        }


        airportHotel(location, checkIn, checkOut)


//row struture for the hotel

        function setHotel(hotel) {
            var htmlHotel = "";
            var contact;

            for (var i = 0; i < hotel.contacts.length; i ++){
               var set = hotel.contacts[i];

            }

            if(set.type === 'URL'){
               contact = "<td>" + '<a href="url:' + hotel.contacts[2].detail + '">'+ hotel.contacts[2].detail +"</a>" + "</td>";
            }
            else{
                contact = "<td>" + '<a href="tel:' + hotel.contacts[0].detail + '">' + hotel.contacts[0].detail + "</a>" + "</td>"
            }


            htmlHotel += "<tr>";
            htmlHotel += "<td>" + hotel.property_name + "</td>";
            htmlHotel += "<td>" + hotel.address.line1 + ', '+ hotel.address.city +' '+  hotel.address.postal_code   +"</td>";
            htmlHotel += contact;
            htmlHotel += "<td>" + "$" +hotel.total_price.amount + "</td>";
            htmlHotel += "</tr>";
            return htmlHotel;


        }


    });

});


/* =======================================================================
                    car rental
========================================================================*/

$(function () {
    document.getElementById("carRental").addEventListener('click', function () {

        var loc = "&location=" + airportCode;
        var pickup = "&pick_up=" + $("#pickUp").val();
        var dropoff = "&drop_off=" + $("#dropOff").val();


        var url_rental = "https://api.sandbox.amadeus.com/v1.2/cars/search-airport?apikey=" + apiKey + loc + pickup + dropoff;

        var request = $.get(url_rental);
        request.fail(function (current, status, error) {
            console.log(status);
            console.log(error);
        });
        console.log(request);
        request.done(function (response) {
            console.log(response);
            for (var i=0;i<response.results.length;i++){
                $("#cars").append(setRental(response.results[i]));
            }

        });


        //function to create the rental Entity
        function setRental(rental) {

            var htmlRental = "";
            htmlRental += "<tr>";
            htmlRental += "<td>" + rental.provider.company_name + "</td>";
            htmlRental += "<td>" + rental.address.line1+ "</td>";
            htmlRental += "<td>" + rental.cars[0].estimated_total.amount +  "</td>";
            htmlRental += "</tr>";
            return htmlRental
        }

    });
});



/* =======================================================================
                    POI Map
========================================================================*/



$(function () {

    document.getElementById("ent").addEventListener('click', function () {



//building the map

        var mapOptions = {
            zoom: 12,
            center: {
                lat: locationAirport.latitude,
                lng: locationAirport.longitude
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
                lat: locationAirport.latitude,
                lng: locationAirport.longitude
            },
            map: map
        });


        function setMarker(location) {
            marker.setPosition(location);

        }


// function to get a marker for each poi on the JSON
        function poiToMarker(data) {
            for (var i = 0; i < data.points_of_interest.length; i++) {
                new google.maps.Marker({
                    position: {
                        lat: locationAirport.latitude,
                        lng: locationAirport.longitude
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

                apikey: apiKey,
                latitude: locationAirport.latitude,
                longitude: locationAirport.longitude,
                radius: '40'
            }).done(function (data) {
            console.log(data);
            poiToMarker(data);

        });


//creating the div for the view

        function createReport(poi) {
            var htmlPlace = "";
            htmlPlace += "<div class='screen'>";
            htmlPlace += "<h3>" + poi.title + "</h3>" + "\n";
            htmlPlace += "<img src=" + poi.main_image + ">" + "\n";
            htmlPlace += "<p> " + poi.details.description + "</p>" + "\n";
            htmlPlace += "</div>";
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
            apikey: apiKey,
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

