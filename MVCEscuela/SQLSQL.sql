PGDMP             
            x            electiva    12.1    12.1     �
           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �
           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                        0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    24589    electiva    DATABASE     �   CREATE DATABASE electiva WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Spain.1252' LC_CTYPE = 'Spanish_Spain.1252';
    DROP DATABASE electiva;
                postgres    false            �            1259    24590    persona    TABLE     E  CREATE TABLE public.persona (
    apellido character varying(15),
    domicilio character varying(30),
    genero character varying(10),
    nombre character varying(15),
    telefono character varying(15),
    nacionalidad character varying(1),
    fecha_nacimiento character varying(10),
    clave character varying(10)
);
    DROP TABLE public.persona;
       public         heap    postgres    false            �
          0    24590    persona 
   TABLE DATA           w   COPY public.persona (apellido, domicilio, genero, nombre, telefono, nacionalidad, fecha_nacimiento, clave) FROM stdin;
    public          postgres    false    202   3       �
   I   x�+.M,J��L*J,����uv�����L�+��ϫ�4014156747��t�4��4�4226��4������ �t�     