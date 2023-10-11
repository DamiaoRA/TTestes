CREATE TABLE public.tb_user
(
  id bigint NOT NULL,
  email character varying(255) NOT NULL,
  name character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  CONSTRAINT tb_user_pkey PRIMARY KEY (id)
);
CREATE TABLE public.tb_role
(
  id bigint NOT NULL,
  role character varying(255) NOT NULL,
  CONSTRAINT tb_role_pkey PRIMARY KEY (id),
  CONSTRAINT tb_role_role_key UNIQUE (role)
);
CREATE TABLE public.tb_user_role
(
  role_id bigint NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT fk_user_id FOREIGN KEY (user_id)
      REFERENCES public.tb_user (id),
  CONSTRAINT fk_role_id FOREIGN KEY (role_id)
      REFERENCES public.tb_role (id)
);

--create table tb_user (id bigint not null, email varchar(255) not null, name varchar(255) not null, password varchar(255) not null, role varchar(255) not null, primary key (id))