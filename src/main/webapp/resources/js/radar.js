var jsVarList = [];
var dataList = [];


function pushSkill(val, data) {
    console.log(val);
    jsVarList.push(val);
    console.log(jsVarList);
    dataList.push(data);
    console.log(dataList);
}


function plotRadar() {

    data = [{
      type: 'scatterpolar',
      r: dataList,
      theta: jsVarList,
      fill: 'toself'
      }
    ]

    layout = {
      polar: {
        radialaxis: {
          visible: true,
          range: [0, 50]
        }
      },
      showlegend: false
    }

    Plotly.plot("chartDiv", data, layout, {showSendToCloud: true})

}