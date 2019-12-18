//jsVarList stocheaza numele skill-urilor care vor aparea pe radar chart
//dataList stocheaza evaluarile pentru fiecare skill in parte

var jsVarList = [];
var dataList = [];

//adauga cate un skill si o evaluare la fiecare iteratie care are loc in fisierul jsp
function pushSkill(val, data) {
    jsVarList.push(val);
    dataList.push(data);
}

//functie pentru plotarea radar chart-ului din libraria plotly
function plotRadar() {
    //se adauga primele valori la sfarsit pentru
    //a se crea intre punctele de pe grafic o forma geometrica inchisa
    dataList.push(dataList[0]);
    jsVarList.push(jsVarList[0]);

    //obiectul care contine toate skill-urile si evaluarile respective care va fi utilizat in
    //afisarea graficului
    data = [{
      type: 'scatterpolar',
      r: dataList,
      theta: jsVarList,
      fill: 'toself'
      }
    ]

    //setari pentru afisarea graficului
    layout = {
      polar: {
        radialaxis: {
          visible: true,
          range: [0, 5]
        }
      },
      showlegend: false
    }

    Plotly.plot("chartDiv", data, layout, {showSendToCloud: true})

}