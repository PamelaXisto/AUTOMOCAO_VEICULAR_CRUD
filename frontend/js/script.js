const cardContainer = document.getElementById('cardContainer');
const semiNovoContainer = document.getElementById('semiNovoContainer');
const toggleThemeBtn = document.getElementById('toggleThemeBtn');


let vehicles = [];

// Carregar os veiculos do servidor
async function loadCards() {
    try {
        const response = await fetch('http://localhost:8080/api/veiculos/listarVeiculos');
        vehicles = await response.json();
        renderCards();
    } catch (error) {
        console.error('Erro ao carregar veículos:', error);
    }
}

// Renderizar os veiculos na tela
function renderCards() {
    cardContainer.innerHTML = '';
    semiNovoContainer.innerHTML = '';

    vehicles.forEach((vehicle) => {
        console.log(vehicle);

        // Criar o card
        const card = document.createElement('div');
        card.className = 'card';

    
        const image = document.createElement('img');
        image.src = vehicle.foto || 'https://via.placeholder.com/150';
        card.appendChild(image);

        const model = document.createElement('h3');
        model.classList.add('vehicle-model'); 
        model.textContent = `${vehicle.modelo} (${vehicle.ano})`;
        card.appendChild(model);

        const brand = document.createElement('p');
        brand.className = 'brand';
        brand.textContent = `Marca: ${vehicle.marca}`;
        card.appendChild(brand);

        const mileage = document.createElement('p');
        mileage.className = 'mileage';
        mileage.textContent = `Quilometragem: ${vehicle.quilometragem} km`;
        card.appendChild(mileage);

        const fuel = document.createElement('p');
        fuel.className = 'fuel';
        fuel.textContent = `Combustível: ${vehicle.combustivel}`;
        card.appendChild(fuel);

        const availability = document.createElement('p');
        availability.className = 'availability';
        availability.textContent = `Disponibilidade: ${vehicle.disponibilidade}`;
        card.appendChild(availability);

        const price = document.createElement('p');
        price.className = 'price'; 
        price.textContent = `Preço Sugerido: R$ ${vehicle.preco}`;
        card.appendChild(price);

        const editButton = document.createElement('button');
        editButton.textContent = 'Editar';
        editButton.onclick = () => editVehicle(vehicle);
        card.appendChild(editButton);

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Deletar';
        deleteButton.onclick = () => deleteVehicle(vehicle.id);
        card.appendChild(deleteButton);


        // Verificar quilometragem para catalogar como novo ou semi-novo
        if (vehicle.quilometragem <= 1) {
            cardContainer.appendChild(card); // Adicionar ao catálogo de veículos novos
        } else {
            semiNovoContainer.appendChild(card); // Adicionar ao catálogo de semi-novos
        }


    });
}

// Adicionar um novo veículo
async function addVehicle() {
    const model = document.getElementById('modelInput').value;
    const year = document.getElementById('yearInput').value;
    const brand = document.getElementById('brandInput').value;
    const mileage = document.getElementById('mileageInput').value;
    const fuel = document.getElementById('fuelInput').value;
    const availability = document.getElementById('availabilityInput').value;
    const price = document.getElementById('priceInput').value;
    const image = document.getElementById('imageInput').value;

    // Verifica se os campos obrigatórios estão preenchidos
    if (model && year && brand) {
        const newVehicle = {
            modelo: model,
            ano: year,
            marca: brand,
            quilometragem: mileage,
            combustivel: fuel,
            disponibilidade: availability,
            preco: price,
            foto: image
        };

        try {
            const response = await fetch('http://localhost:8080/api/veiculos/cadastrarVeiculos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newVehicle),
            });

            if (response.ok) {
                loadCards(); 
                clearForm(); 
            } else {
                console.error('Erro ao cadastrar o veículo:', await response.text());
                clearForm();
            }
        } catch (error) {
            console.error('Erro ao cadastrar veículo:', error);
            clearForm();
        }
    } else {
        alert('Por favor, preencha todos os campos obrigatórios (modelo, ano, marca, preco e foto).');
    }
}


// Editar um veículo já existente
async function editVehicle(vehicle) {
    document.getElementById('modelInput').value = vehicle.modelo;
    document.getElementById('yearInput').value = vehicle.ano;
    document.getElementById('brandInput').value = vehicle.marca;
    document.getElementById('mileageInput').value = vehicle.quilometragem;
    document.getElementById('fuelInput').value = vehicle.combustivel;
    document.getElementById('availabilityInput').value = vehicle.disponibilidade;
    document.getElementById('priceInput').value = vehicle.preco;
    document.getElementById('imageInput').value = vehicle.foto;

    window.scrollTo(0, 0);

    // Alterar o botão "Adicionar" para "Salvar Alterações"
    const addButton = document.querySelector('.form-section button');
    addButton.textContent = 'Salvar Alterações';
    addButton.onclick = () => saveEdit(vehicle.id);
}

// Salvar as alterações do veiculo
async function saveEdit(vehicleid) {
    const model = document.getElementById('modelInput').value;
    const year = document.getElementById('yearInput').value;
    const brand = document.getElementById('brandInput').value;
    const mileage = document.getElementById('mileageInput').value;
    const fuel = document.getElementById('fuelInput').value;
    const availability = document.getElementById('availabilityInput').value;
    const price = parseFloat(document.getElementById('priceInput').value.replace(',', '.'));
    const image = document.getElementById('imageInput').value;


    if (availability.toLowerCase() === "vendido") {
        try {
            const deleteResponse = await fetch(`http://localhost:8080/api/veiculos/deletarVeiculo/${vehicleid}`, {
                method: 'DELETE',
            });

            if (deleteResponse.ok) {
                console.log(`Veículo ID ${vehicleid} removido.`);
                loadCards(); // Atualiza a lista de veículos
            } else {
                console.error('Erro ao remover veículo:', await deleteResponse.text());
            }
        } catch (error) {
            console.error('Erro ao remover veículo:', error);
        }
        return; // Sai da função após remover o veículo
    }


    // Verificar se os campos obrigatórios estão preenchidos
        const updatedVehicle = {
            modelo: model,
            ano: year,
            marca: brand,
            quilometragem: mileage,
            combustivel: fuel,
            disponibilidade: availability,
            preco: price,
            foto: image
        };

        try {
            const response = await fetch(`http://localhost:8080/api/veiculos/editarVeiculo/${vehicleid}
            `, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updatedVehicle),
            });

            if (response.ok) {
                loadCards();
                clearForm();
                const addButton = document.querySelector('.form-section button');
                addButton.textContent = 'Adicionar Veículo';
                addButton.onclick = addVehicle;
            } else {
                console.error('Erro ao editar veículo:', await response.text());
            }
        } catch (error) {
            console.error('Erro ao editar veículo:', error);
        }
}

// Deletar um veículo
async function deleteVehicle(id) {
    try {
        const response = await fetch(`http://localhost:8080/api/veiculos/deletarVeiculo/${id}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            loadCards();
        } else {
            console.error('Erro ao deletar veiculo:', await response.text());
        }
    } catch (error) {
        console.error('Erro ao deletar veiculo:', error);
    }
}

// Limpar o formulário após adicionar ou editar um veículo
function clearForm() {
    document.getElementById('modelInput').value = '';
    document.getElementById('yearInput').value = '';
    document.getElementById('brandInput').value = '';
    document.getElementById('mileageInput').value = '';
    document.getElementById('fuelInput').value = '';
    document.getElementById('availabilityInput').value = '';
    document.getElementById('priceInput').value = '';
    document.getElementById('imageInput').value = '';
}


// Faz a busca do veículo desejado
function searchVehicle() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
 
    if (!searchInput) {
         alert('Insira a marca do veículo desejado para realizar a busca!')
         return;
    }
 
    const filteredVehicles = vehicles.filter(vehicle =>
         vehicle.marca.toLowerCase().includes(searchInput)
    );
 
    if (filteredVehicles.length === 0) {
         alert('O veículo solicitado não encontra-se disponível em nossa concessionária!');
    } else {
         renderFilteredCards(filteredVehicles);
    }
 }

// Renderizar apenas os veículos filtrados
function renderFilteredCards(filteredVehicles) {
    cardContainer.innerHTML = ''; // Limpa os cards existentes
    filteredVehicles.forEach((vehicle) => {
        const card = document.createElement('div');
        card.className = 'card';

        const image = document.createElement('img');
        image.src = vehicle.foto || 'https://via.placeholder.com/150';
        card.appendChild(image);

        const model = document.createElement('h3');
        model.textContent = vehicle.modelo;
        card.appendChild(model);

        const year = document.createElement('p');
        year.textContent = `Ano: ${vehicle.ano}`;
        card.appendChild(year);

        const brand = document.createElement('p');
        brand.textContent = `Marca: ${vehicle.marca}`;
        card.appendChild(brand);

        const mileage = document.createElement('p');
        mileage.textContent = `Quilometragem: ${vehicle.quilometragem}`;
        card.appendChild(mileage);

        const fuel = document.createElement('p');
        fuel.textContent = `Combustível: ${vehicle.combustivel}`;
        card.appendChild(fuel);

        const availability = document.createElement('p');
        availability.textContent = `Disponibilidade: ${vehicle.disponibilidade}`;
        card.appendChild(availability);

        const price = document.createElement('p');
        price.textContent = `Preço: ${vehicle.preco.toFixed(2).replace(',', '.')}`;
        card.appendChild(price);



        const editButton = document.createElement('button');
        editButton.textContent = 'Editar';
        editButton.onclick = () => editVehicle(vehicle);
        card.appendChild(editButton);

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Deletar';
        deleteButton.onclick = () => deleteVehicle(vehicle.id);
        card.appendChild(deleteButton);

        cardContainer.appendChild(card);
    });
}

// Alternar entre tema claro e escuro
toggleThemeBtn.addEventListener('click', () => {
    document.body.classList.toggle('dark-theme');
});

// Inicializar com os livros carregados
loadCards();
