# assesment_great_eastern_group

# DATABASE
CREATE DATABASE imam;

CREATE TABLE items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_item_name (name)
);

CREATE TABLE variants (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    stock INT NOT NULL,
    item_id BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_variant_item
        FOREIGN KEY (item_id)
        REFERENCES items(id)
        ON DELETE CASCADE,
    INDEX idx_variant_item_id (item_id),
    INDEX idx_variant_stock (stock)
);


CREATE TABLE sales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    variant_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(15,2) NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    sold_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_sale_variant
        FOREIGN KEY (variant_id)
        REFERENCES variants(id)
        ON DELETE RESTRICT
);



# POSTMAN
1. Create Item
    http://localhost:8080/api/items
    method POST 
    Requeset boody 
    JSON
    {
      "name": "T-Shirt"
    }

    Success Response
    JSON
    {
      "id": 1,
      "name": "T-Shirt",
      "createdAt": "2026-02-24T14:50:21"
    }
    
    Error Response if item name laready exists
    {
        "error": "Item already exists"
    }


2. Get All Item
    http://localhost:8080/api/items
    method GET 

    Success Response
    JSON
    [
      {
          "id": 1,
          "name": "T-Shirt",
          "createdAt": "2026-02-24T14:36:41.258654"
      },
      {
          "id": 2,
          "name": "Jacket",
          "createdAt": "2026-02-24T14:40:58.924061"
      }
    ]

3. Get Item by ID
    http://localhost:8080/api/items/{id}
    
    Ex : http://localhost:8080/api/items/1
    method POST 

    Success Response
    JSON
    {
        "id": 1,
        "name": "T-Shirt",
        "createdAt": "2026-02-24T14:36:41.258654"
    }
    
    Error Response
    {
        "error": "Item not found"
    }
    
 4. Create Variant
    http://localhost:8080/api/variants
    
    Method POST
    
    Request Body JSON
    {
      "itemId": 2,
      "name": "Size L Black",
      "price": 500000,
      "stock": 10
    }
    
    Success Respon
    {
        "id": 7,
        "name": "Size XL Black",
        "price": 500000,
        "stock": 10,
        "itemId": 2,
        "itemName": "Jacket"
    }
    Success Respon if id and name already exists will increase the stock
    
    {
        "id": 7,
        "name": "Size XL Black",
        "price": 500000.00,
        "stock": 20,
        "itemId": 2,
        "itemName": "Jacket"
    }
    
    Error Respon if itemId not exists
    {
        "error": "Item not found"
    }
 
 5. Get Variant By Item ID
    http://localhost:8080/api/variants/item/{itemId}
    Ex : http://localhost:8080/api/variants/item/1
    Method GET
    
    Success Respon
    [
        {
            "id": 1,
            "name": "Size L Black",
            "price": 150000.00,
            "stock": 7,
            "itemId": 1,
            "itemName": "T-Shirt"
        },
        {
            "id": 2,
            "name": "Size M Black",
            "price": 150000.00,
            "stock": 10,
            "itemId": 1,
            "itemName": "T-Shirt"
        }
    ]


6. Sell
   http://localhost:8080/api/sales
   
   Method POST
   
   Request Body
   JSON
    {
      "variantId": 1,
      "quantity": 3
    }
    
    Success Response
    JSON
    {
        "saleId": 1,
        "variantId": 1,
        "variantName": "Size L Black",
        "quantity": 3,
        "unitPrice": 150000.00,
        "totalAmount": 450000.00,
        "soldAt": "2026-02-24T21:03:28.663323"
    }
    
 7.Delete Item
    http://localhost:8080/api/items/{itemId}
    ex : http://localhost:8080/api/items/1
    
    
    Method DELETE
    on success 204 NO CONTENT
    
    Error REspoinse if itemId note exists
    {
        "error": "Item not found"
    }
    
    Error Response constrain with varriant
    {
        "error": "Cannot delete item with existing variants"
    }
 