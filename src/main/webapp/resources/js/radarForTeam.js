var jsVarListTeam = [];
var dataListTeam = [];
var allData = [];
var numeProiect;
var targetData = [];


function pushSkillTeam(value, temp) {

    jsVarListTeam.push(value);
    dataListTeam.push(temp);

}

function pushTargetVal(targt){
    targetData.push(targt);
}


var targetObject = {
    type: 'scatterpolar',
    r: targetData,
    theta: jsVarListTeam,
    name:'Necesar',
};

allData.push(targetObject);

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

    targetData.push(targetData[0]);
    console.log(targetData);
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




