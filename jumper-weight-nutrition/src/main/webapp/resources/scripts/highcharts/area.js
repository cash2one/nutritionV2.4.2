$(function () {
    $('#container').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: '近7天设备使用人数及频次'
        },
        subtitle: {
            text: 'Number and Frequency'
        },
        xAxis: {
            categories: ['3/1', '3/2', '3/3', '3/4', '3/5', '3/6','3/7']
        },
        yAxis: {
            title: {
                text: '近7天使用情况'
            }
        },
        tooltip: {
            enabled: false,
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C';
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: false
            }
        },
        series: [{
            name: '人数',
            data: [20, 135, 203, 302, 432, 520, 700]
        }, {
            name: '频次',
            data: [39, 42, 57, 85, 119, 152, 170]
        }]
    });
});