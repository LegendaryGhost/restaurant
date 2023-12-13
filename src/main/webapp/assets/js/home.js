/****************  Functions ****************/

const generate_tr = object => {
    const tr = document.createElement('tr');
    Object.keys(object).forEach(cle => {
        const td = document.createElement('td');
        td.textContent = object[cle];
        tr.appendChild(td);
    });
    return tr;
};

/**
 * Creates a div element from a dish
 * @param dish
 * @returns {HTMLDivElement}
 */
const create_dish_div = dish => {
    // <div class="col-lg-4 menu-item">
    //     <a href="../assets/img/menu/<%= dish.getImage() %>" class="glightbox">
    //         <img src="../assets/img/menu/<%= dish.getImage() %>" class="menu-img img-fluid" alt="">
    //     </a>
    //     <h4><%= dish.getName() %></h4>
    //     <p class="price">
    //         <%= df.format(dish.getPrice()) + " Ar" %>
    //     </p>
    //     <button class="btn btn-danger rounded-5">Ajouter à la commande</button>
    // </div>
    const dish_div = document.createElement('div'),
        link = document.createElement('a'),
        img = document.createElement('img'),
        h4 = document.createElement('h4'),
        price = document.createElement('p'),
        add_btn = document.createElement('button');

    dish_div.className = 'col-lg-4 menu-item';
    link.href = `../assets/img/menu/${dish.image}`;
    link.className = 'glightbox';
    img.src = `../assets/img/menu/${dish.image}`;
    img.className = 'menu-img img-fluid';
    img.alt = dish.name;
    price.className = 'price';
    add_btn.className = 'btn btn-danger rounded-5';
    add_btn.dataset.dish_id = dish.id;

    h4.textContent = dish.name;
    price.textContent = dish.price + ' Ar';
    add_btn.textContent = 'Ajouter à la commande';

    add_btn.addEventListener('click', add_to_cart);

    link.appendChild(img);
    dish_div.append(link, h4, price, add_btn);

    return dish_div;
};

const create_cart_item = dish => {
    // <div class="cart-item">
    //     <div className="row mb-4 d-flex justify-content-between align-items-center">
    //         <div className="col-md-2 col-lg-2 col-xl-2">
    //             <img
    //                 src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-shopping-carts/img5.webp"
    //                 className="img-fluid rounded-3" alt="Cotton T-shirt">
    //         </div>
    //         <div className="col-md-3 col-lg-3 col-xl-3">
    //             <h6 className="text-muted">Shirt</h6>
    //             <h6 className="text-black mb-0">Cotton T-shirt</h6>
    //         </div>
    //         <div className="col-md-3 col-lg-3 col-xl-2 d-flex">
    //             <button className="btn btn-link px-2"
    //                     onClick="this.parentNode.querySelector('input[type=number]').stepDown()">
    //                 <i className="bi-dash-lg"></i>
    //             </button>
    //
    //             <input min="1" name="quantity" value="1" type="number"
    //                    className="form-control form-control-sm"/>
    //
    //             <button className="btn btn-link px-2"
    //                     onClick="this.parentNode.querySelector('input[type=number]').stepUp()">
    //                 <i className="bi-plus-lg"></i>
    //             </button>
    //         </div>
    //         <div className="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
    //             <h6 className="mb-0">€ 44.00</h6>
    //         </div>
    //         <div className="col-md-1 col-lg-1 col-xl-1 text-end">
    //             <a href="#!" className="text-muted"><i className="fas fa-times"></i></a>
    //         </div>
    //     </div>
    //
    //     <hr className="my-4">
    // </div>

    if (dish === undefined) return;

    // Create the cart item structure
    const row = document.createElement('div');
    row.className = 'cart-item row mb-4 d-flex justify-content-between align-items-center';
    row.dataset.dish_id = dish.id;

    const img_div = document.createElement('div');
    img_div.className = 'col-md-2 col-lg-2 col-xl-2';
    const img = document.createElement('img');
    img.src = `../assets/img/menu/${dish.image}`;
    img.className = 'img-fluid rounded-3';
    img.alt = dish.name;
    img_div.appendChild(img);

    const dish_details_div = document.createElement('div');
    dish_details_div.className = 'col-md-3 col-lg-3 col-xl-3';
    const dish_name = document.createElement('h4');
    dish_name.className = 'text-black text-center my-3';
    dish_name.textContent = dish.name;
    dish_details_div.appendChild(dish_name);

    const quantity_div = document.createElement('div');
    quantity_div.className = 'col-md-3 col-lg-3 col-xl-2 d-flex';
    const minus_button = document.createElement('button');
    minus_button.className = 'btn btn-link px-2';
    minus_button.onclick = function() {
        this.parentNode.querySelector('input[type=number]').stepDown();
    };
    const minus_icon = document.createElement('i');
    minus_icon.className = 'bi-dash-lg';
    minus_button.appendChild(minus_icon);
    const plus_button = document.createElement('button');
    plus_button.className = 'btn btn-link px-2';
    plus_button.onclick = function() {
        this.parentNode.querySelector('input[type=number]').stepUp();
    };
    const plus_icon = document.createElement('i');
    plus_icon.className = 'bi-plus-lg';
    plus_button.appendChild(plus_icon);
    const quantity_input = document.createElement('input');
    quantity_input.min = '1';
    quantity_input.name = 'quantity';
    quantity_input.value = '1';
    quantity_input.type = 'number';
    quantity_input.className = 'form-control form-control-sm';
    quantity_div.appendChild(minus_button);
    quantity_div.appendChild(quantity_input);
    quantity_div.appendChild(plus_button);

    const price_div = document.createElement('div');
    price_div.className = 'col-md-3 col-lg-2 col-xl-2 offset-lg-1';
    const price = document.createElement('h6');
    price.className = 'my-3 text-center price';
    price.textContent = `${dish.price} Ar`;
    price_div.appendChild(price);

    const remove_button = document.createElement('div');
    remove_button.className = 'col-md-1 col-lg-1 col-xl-1 text-center';
    const remove_link = document.createElement('a');
    remove_link.className = 'text-center';
    const remove_icon = document.createElement('i');
    remove_icon.className = 'bi-x';
    remove_link.appendChild(remove_icon);
    remove_link.addEventListener('click', ev => {
        ev.preventDefault();
        ev.stopPropagation();
        const cart_item = ev.target.parentElement.parentElement.parentElement;
        const dish_id = cart_item.dataset.dish_id;
        dishes_in_cart = dishes_in_cart.filter(dish => dish.id !== parseInt(dish_id));
        cart_item.nextSibling.remove();
        cart_item.remove();
    });
    remove_button.appendChild(remove_link);
    remove_button.style.cursor = 'pointer';
    remove_button.addEventListener('click', event => {
        const cart_item = event.target.parentElement;
        const dish_id = cart_item.dataset.dish_id;
        dishes_in_cart = dishes_in_cart.filter(dish => dish.id !== parseInt(dish_id));
        cart_item.nextSibling.remove();
        cart_item.remove();
    });

    row.appendChild(img_div);
    row.appendChild(dish_details_div);
    row.appendChild(quantity_div);
    row.appendChild(price_div);
    row.appendChild(remove_button);

    return row;
};

const add_to_cart = ev => {
    const dish_id = ev.target.dataset.dish_id;
    const cart_item = cart.querySelector(`div[data-dish_id="${dish_id}"]`);

    if (cart_item) {
        const quantity_input = cart_item.querySelector('input[type=number]');
        quantity_input.stepUp();
    } else {
        const dish = dishes.find(d => d.id === parseInt(dish_id));
        if (dish !== undefined) {
            dishes_in_cart.push(dish);
            const hr = document.createElement('hr');
            hr.className = 'my-4';
            cart.appendChild(create_cart_item(dish));
            cart.appendChild(hr);
        }
    }
};

const show_dishes = () => {
    sendXHRRequest("../dishes-servlet").then(
        response => {
            try {
                dishes = JSON.parse(response);
                for (const dish of dishes) {
                    dishes_container.appendChild(create_dish_div(dish));
                }
            } catch (e) {
                console.error(e);
                console.log(response);
            }
        }
    );
};

const show_suggesters = () => {
    sendXHRRequest('../suggester-servlet').then(
        response => {
            try {
                suggesters = JSON.parse(response);

                for (const suggester of suggesters) {
                    const option = document.createElement('option');
                    option.value = suggester.id;
                    option.textContent = suggester.firstname + ' ' + suggester.lastname;
                    suggester_select.appendChild(option);
                }
            } catch (e) {
                console.log(response);
                console.error(e);
            }
        }
    );
};

const show_statistics = () => {
    sendXHRRequest('../statistics-servlet').then(
        response => {
            try {
                const stats = JSON.parse(response);
                income_span.dataset.purecounterEnd = stats.income;
                total_commission_span.dataset.purecounterEnd = stats.total_commission;
                expense_span.dataset.purecounterEnd = stats.expense;
                net_income_span.dataset.purecounterEnd = stats.income - stats.expense - stats.total_commission;
            } catch (e) {
                console.log(response);
                console.error(e);
            }
        }
    );
};

const show_years = () => {
    const date = new Date();
    const current_year = date.getFullYear();

    for (let year = 2020; year <= current_year; year++) {
        const option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        year_select.appendChild(option);
    }
};

const show_year_month_stats = () => {
    const year = year_select.value;
    const month = month_select.value;
    const form_data = new FormData();
    form_data.append('year', year);
    form_data.append('month', month);
    sendXHRRequest('../suggester-stats-servlet', 'POST', form_data).then(
        response => {
            try {
                date_stats_tbody.innerText = "";
                const stats_array = JSON.parse(response);
                for (const stats of stats_array) {
                    date_stats_tbody.appendChild(generate_tr(stats));
                }
            } catch (e) {
                console.log(response);
                console.error(e);
            }
        }
    );

    sendXHRRequest('../date-statistics-servlet', 'POST', form_data).then(
        response => {
            try {
                const stats = JSON.parse(response);
                if (Object.keys(stats).length === 0) {
                    stats.income = 0;
                    stats.total_commission = 0;
                    stats.expense = 0;
                }
                date_income_span.textContent = stats.income.toString();
                date_total_commission_span.textContent = stats.total_commission.toString();
                date_expense_span.textContent = stats.expense.toString();
                date_net_income_span.textContent = (stats.income - stats.expense - stats.total_commission).toString();
            } catch (e) {
                console.log(response);
                console.error(e);
            }
        }
    );
}

const select_cur_date = () => {
    const date = new Date();
    const current_year = date.getFullYear();
    const current_month = date.getMonth() + 1;

    year_select.value = current_year;
    month_select.value = current_month;
};

const send_command = () => {
    if (dishes_in_cart.length === 0) {
        alert("Impossible d'envoyer une commande vide");
    } else {
        const id_suggester = suggester_select.value;
        const formData = new FormData();
        formData.append('id_suggester', id_suggester === '' ? null : id_suggester);
        sendXHRRequest('../command-servlet', 'POST', formData).then(
            response => {
                try {
                    const id_command = JSON.parse(response).id;
                    for (const dish of dishes_in_cart) {
                        const cart_item = document.querySelector(`div[data-dish_id='${dish.id}']`)
                        const quantity = cart_item.querySelector(`input[type=number]`).value;
                        const containData = new FormData();
                        containData.append('id_dish', dish.id);
                        containData.append('id_command', id_command);
                        containData.append('quantity', quantity);
                        sendXHRRequest('../contain-servlet', 'POST', containData).then(
                            () => {
                                cart_item.nextSibling.remove();
                                cart_item.remove();
                            }
                        );
                    }
                    alert('Commande effectuée');
                } catch (e) {
                    console.log(response);
                    console.error(e);
                }
            }
        );
    }
};


/****************  Global variables ****************/
let dishes = [],
    dishes_in_cart = [],
    suggesters = [];


/****************  HTML elements ****************/
let dishes_container,
    cart,
    command_btn,
    suggester_select,
    income_span,
    total_commission_span,
    expense_span,
    net_income_span,
    date_income_span,
    date_total_commission_span,
    date_expense_span,
    date_net_income_span,
    year_select,
    month_select,
    date_stats_tbody;


// Code to execute when the page is loaded
window.addEventListener('load', () => {
    // Get all HTML elements
    dishes_container = document.getElementById('dishes');
    cart = document.getElementById('cart');
    command_btn = document.getElementById('command_btn');
    suggester_select = document.getElementById('id_suggester');
    income_span = document.getElementById('income');
    total_commission_span = document.getElementById('total-commission');
    expense_span = document.getElementById('expense');
    net_income_span = document.getElementById('net-income');
    date_income_span = document.getElementById('date-income');
    date_total_commission_span = document.getElementById('date-total-commission');
    date_expense_span = document.getElementById('date-expense');
    date_net_income_span = document.getElementById('date-net-income');
    year_select = document.getElementById('year');
    month_select = document.getElementById('month');
    date_stats_tbody = document.getElementById('date-stats');

    // Event listeners
    command_btn.addEventListener('click', send_command);
    month_select.addEventListener('change', () => {
        show_year_month_stats();
    });
    year_select.addEventListener('change', () => {
        show_year_month_stats();
    });

    show_years();
    select_cur_date();
    show_dishes();
    show_suggesters();
    show_statistics();
    show_year_month_stats();
});
