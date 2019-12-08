var jsVarListTeam = [];
var dataListTeam = [];
var allData = [];
var numeProiect;


function pushSkillTeam(value, temp) {
    console.log(value);
    jsVarListTeam.push(value);
    console.log(jsVarListTeam);
    dataListTeam.push(temp);
    console.log(dataListTeam);
}

function plotRadarTeam() {

    layout = {
    title: numeProiect,
      polar: {
        radialaxis: {
          visible: true,
          range: [0, 5]
        }
      },
      showlegend: true
    };

    Plotly.plot("chart", allData, layout);

}

function pushAllUsers(nume, numePrj) {
    dataListTeam.push(dataListTeam[0]);
    jsVarListTeam.push(jsVarListTeam[0]);
    console.log(jsVarListTeam);
    var newObject = {
        type: 'scatterpolar',
        r: dataListTeam,
        theta: jsVarListTeam,
    };

    numeProiect = numePrj;
    console.log(nume);
    console.log(numeProiect);
    console.log(numePrj);
    newObject.name = nume;

    allData.push(newObject);

    jsVarListTeam = [];
    dataListTeam = [];
}

