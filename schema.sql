CREATE ROLE xfr LOGIN
  PASSWORD 'xfr'
  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;

  
  
CREATE DATABASE xfr
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Russian_Russia.1251'
       LC_CTYPE = 'Russian_Russia.1251'
       CONNECTION LIMIT = -1;


CREATE TABLE xfr.public.entry
(
  id bigint NOT NULL,
  content character varying(1024),
  creationdate timestamp without time zone,
  CONSTRAINT entry_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE xfr.public.entry
  OWNER TO xfr;
