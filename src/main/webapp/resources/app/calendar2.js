document.addEventListener('DOMContentLoaded', function() {

    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
    schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
    plugins: [ 'interaction', 'dayGrid', 'timeGrid', 'resourceTimeline'],
    now: Date.now,
    height: "auto",
    editable: true,
    resizable: true,
    aspectRatio: 2.0,
    scrollTime: '00:00', // undo default 6am scrollTime
    header: {
    left: 'today prev,next',
    center: 'title',
    right: 'dayGridMonth,resourceTimelineMonth',

},
    defaultView: 'resourceTimelineMonth',

    resourceLabelText: 'Technician',
    resources: '/techniciansList/get',
    displayEventTime: false,
    eventSources: [
        {
            url: '/serviceTicketWarranty/get',
            color: 'red',
            textColor: 'white',
        },
        {
            url: '/serviceTicketAfterWarranty/get',
            color: 'orange',
            textColor: 'white',
        },
        {
            url: '/serviceTicketAssemble/get',
            color: 'green',
            textColor: 'white',
        }
    ],
        eventResize:function(eventResizeInfo, delta, event) {

            const id = eventResizeInfo.event.id;

            var finalStart = eventResizeInfo.event.start;

            var finalEnd = eventResizeInfo.event.end;


            $.ajax({
                url: '/serviceTicket/put/' + id,
                data: JSON.stringify({
                    id: id,
                    start: finalStart,
                    end: finalEnd,
                }),
                type: "POST",
                dataType: "JSON",
                contentType: "application/json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },

                success:function(){
                    calendar.render();
                    alert('Event Update');
                }

            })
                .done(function () {
                    calendar.render();
                });
        },

        eventDrop:function(eventDropInfo, event) {
            const id = eventDropInfo.event.id;

            var finalStart = eventDropInfo.event.start;

            var finalEnd = eventDropInfo.event.end;

            var dataResources = eventDropInfo.event.getResources();

            var newId = dataResources[0].id;


            $.ajax({
                url: '/serviceTicket/drop/' + id,
                data: JSON.stringify({
                    id: id,
                    start: finalStart,
                    end: finalEnd,
                    resourceId: newId,
                }),
                type: "POST",
                dataType: "JSON",
                contentType: "application/json",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },

                success:function(){
                    calendar.render();
                    alert('Event Update');
                }

            })
                .done(function () {
                    calendar.render();
                });
        },

        eventClick:function(event)
        {
            if(confirm("Are you sure you want to remove it?"))
            {
                const id = event.event.id;
                $.ajax({
                    url: '/serviceTicket/delete/' + id,
                    data: {},
                    type: "DELETE",
                    dataType: "JSON",
                    contentType: "application/json",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },

                    success:function(){
                        calendar.fullCalendar('refetchEvents');
                        alert('Event Update');
                        location.reload();
                    }

                })
                    .done(function () {
                        alert('Event Update');
                        location.reload();
                    });
            }
            location.reload();
        },

});
    calendar.render();
    calendar.location.reload();
});
