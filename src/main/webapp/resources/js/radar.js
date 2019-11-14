var jsVarList = [];



function pushSkill(val) {
    console.log(val);
    jsVarList.push(val);
    console.log(jsVarList);
}


function plotRadar() {

    data = [{
      type: 'scatterpolar',
      r: [1, 2],
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