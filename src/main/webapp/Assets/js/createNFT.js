
      document.getElementById("author").value=localStorage.getItem("username");

      const form = document.getElementById("formUploadNFT");
    uploadNFT = async (e) => {
        e.preventDefault();

        let author = document.getElementById("author").value;
        let collection = document.getElementById("collection").value;

        const response = await fetch(`./api/users/${author}/collections/${collection}/arts`, {
            method: 'POST',
            body: new FormData(form)
        });

        console.log("Se cargo éxitosamente el NFt");

        document.getElementById('frame').src= "./createNFT.html";

    }
    form.addEventListener("submit", uploadNFT);

    
          const getData = async () => {
              const dataCollectionNFTs = await fetch(`./api/users/${localStorage.getItem("username")}/collections`).then(response => response.json());
              const collectionName = dataCollectionNFTs.map(collectionNft => ({collection: collectionNft.collection}))

                  for (var i = 0; i < collectionName.length; i++) {
                      document.getElementById("collection").innerHTML +=
                          `<option style="font-family: 'Abel', sans-serif;">${collectionName[i].collection}</option>`;
                  }

          }
          window.addEventListener("DOMContentLoaded", getData());