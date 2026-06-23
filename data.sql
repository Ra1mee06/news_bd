CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(40) NOT NULL UNIQUE, 
    password VARCHAR(255) NOT NULL,       
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    parent_name VARCHAR(20) NULL,         -
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    edited_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);


CREATE TABLE news (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    text VARCHAR(2000) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    edited_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    inserted_by BIGINT NOT NULL,
    updated_by BIGINT NOT NULL,

    CONSTRAINT news_inserted_by FOREIGN KEY (inserted_by) REFERENCES users(id),
    CONSTRAINT news_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)
);
 

CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(300) NOT NULL,
    news_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    edited_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    inserted_by BIGINT NOT NULL,

    CONSTRAINT comments_inserted_by FOREIGN KEY (inserted_by) REFERENCES users(id)
);
