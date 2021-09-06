$( ()=> {
    getGraphicalData();
    }
)
let memberLineSeries = {debug: true,
    legend_visible: false,
    title_label_text: 'Membership',
    yAxis: [
        { formatString: 'c' },
        {
            id: 'secondY',
            orientation: 'opposite',
            line_color: '#e2e2e2',
            defaultTick: {
                enabled: false,
                gridLine_visible: false
            }
        }
    ],
    xAxis: {
        crosshair_enabled: true,
        scale: { type: 'time' }
    },
    defaultSeries: {
        type: 'line',
        defaultPoint_marker_visible: false,
        lastPoint: {
            label_text: '<b>%seriesName</b>',
            yAxisTick: {
                axisId: 'secondY',
                // label_text: '%yValue'
            }
        }
    }};
let leasablesColumn = { debug: true, defaultSeries_type: 'column'};
let currentRevenue = {};
let leasesByMember = {};
let rentedUnrented = { debug: true,
    title_position: 'center',
    legend: {
        template:
            '%value {%percentOfTotal:n1}% %icon %name',
        position: 'inside left bottom'
    },
    defaultSeries: {
        type: 'pie',
        pointSelection: true
    },
    defaultPoint_label_text: '<b>%name</b>',
    title_label_text: 'Leasables Statuses'};

let leased = 0;
let closed = 0;
let available = 0;

let greenhousePlots = 0;
let outdoorPlots = 0;
let workshops = 0;
let beehives = 0;
let fishtanks = 0;

let memberCount = 0;

function getGraphicalData(){
    memberCount = 0;
    greenhousePlots = 0;
    outdoorPlots = 0;
    workshops = 0;
    beehives = 0;
    fishtanks = 0;
    leased = 0;
    closed = 0;
    available = 0;
    let membershipPoints = [];

    fetch('/api/leasables/get-all')
        .then(response=>response.json())
        .then(data => {
            data.forEach( (row) => {
                console.log(row);
                switch(row['leasableType'].leasableTypeName){
                    case 'outdoorGardenPlot': outdoorPlots++; break;
                    case 'greenhousePlot': greenhousePlots++; break;
                    case 'beeHive': beehives++; break;
                    case 'fishTank': fishtanks++; break;
                    case 'workshop': workshops++; break;
                }
                switch(row['leasableStatus'].leasableStatus){
                    case 'leased': leased++; break;
                    case 'closed': closed++; break;
                    case 'open': available++; break;
                }
            })
            leasablesColumn.series = [
                {
                    name: 'Leasables',
                    hatchPalette: true,
                    defaultPoint_hatch_color: '#000',
                    palette: 'default',
                    points: [
                        {name: 'Outdoor Plot', y: outdoorPlots},
                        {name: 'Greenhouse Plot', y: greenhousePlots},
                        {name: 'Beehive', y: beehives},
                        {name: 'Workshop', y: workshops},
                        {name: 'FishTanks', y: fishtanks}
                    ]
                }
            ]
            rentedUnrented.series = [
                {
                    name:'Leasables Status',
                    points: [
                        {name: 'Rented', y: leased},
                        {name: 'Closed', y: closed},
                        {name: 'Available', y: available}
                    ]
                }
            ]
            JSC.Chart('leasables-column', leasablesColumn);
            JSC.Chart('rented-vs-unrented-pie', rentedUnrented);

            return fetch('/api/users/get-all')
        }).then( response => response.json())
        .then(data => {
            data.forEach((row) =>{
                memberCount++;
                membershipPoints.push([row.joinedDate, memberCount]);
            })
            memberLineSeries.series = [
                {
                    name: 'Members',
                    points: membershipPoints
                }
            ]
            JSC.Chart('members-line-series', memberLineSeries);
        });
}



// JSC.Chart('current-revenue', {});
// JSC.Chart('leases-by-member', {});