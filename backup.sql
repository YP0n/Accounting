--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3 (Homebrew)
-- Dumped by pg_dump version 15.3 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: expense_type_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.expense_type_enum AS ENUM (
    'FOOD',
    'UTILITY',
    'OTHER'
);


ALTER TYPE public.expense_type_enum OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: business_expenses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.business_expenses (
    id integer NOT NULL,
    fuel double precision NOT NULL,
    maintenance double precision NOT NULL,
    salary_v double precision NOT NULL,
    salary_i double precision NOT NULL,
    utility_and_water double precision NOT NULL,
    rent double precision NOT NULL,
    tax_single double precision NOT NULL,
    tax_pension double precision NOT NULL,
    owner double precision NOT NULL,
    suppliers double precision NOT NULL,
    date_expense_business date
);


ALTER TABLE public.business_expenses OWNER TO postgres;

--
-- Name: business_expenses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.business_expenses_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.business_expenses_id_seq OWNER TO postgres;

--
-- Name: business_expenses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.business_expenses_id_seq OWNED BY public.business_expenses.id;


--
-- Name: income_shop; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.income_shop (
    id integer NOT NULL,
    income_cashless double precision NOT NULL,
    income_other double precision NOT NULL,
    date_income date,
    income_cash double precision
);


ALTER TABLE public.income_shop OWNER TO postgres;

--
-- Name: income_shop_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.income_shop_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.income_shop_id_seq OWNER TO postgres;

--
-- Name: income_shop_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.income_shop_id_seq OWNED BY public.income_shop.id;


--
-- Name: personal_expenses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personal_expenses (
    id integer NOT NULL,
    food_expense double precision NOT NULL,
    utility_expense double precision NOT NULL,
    other_expense double precision NOT NULL,
    date_expense_personal date
);


ALTER TABLE public.personal_expenses OWNER TO postgres;

--
-- Name: personal_expenses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.personal_expenses_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.personal_expenses_id_seq OWNER TO postgres;

--
-- Name: personal_expenses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personal_expenses_id_seq OWNED BY public.personal_expenses.id;


--
-- Name: business_expenses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.business_expenses ALTER COLUMN id SET DEFAULT nextval('public.business_expenses_id_seq'::regclass);


--
-- Name: income_shop id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.income_shop ALTER COLUMN id SET DEFAULT nextval('public.income_shop_id_seq'::regclass);


--
-- Name: personal_expenses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personal_expenses ALTER COLUMN id SET DEFAULT nextval('public.personal_expenses_id_seq'::regclass);


--
-- Data for Name: business_expenses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.business_expenses (id, fuel, maintenance, salary_v, salary_i, utility_and_water, rent, tax_single, tax_pension, owner, suppliers, date_expense_business) FROM stdin;
4	0	0	0	966	0	0	0	0	0	0	1970-01-01
5	0	0	2592	0	0	0	0	0	0	0	1970-01-01
6	0	0	1748	0	0	0	0	0	0	0	1970-01-01
7	0	0	1642	0	0	0	0	0	0	0	1970-01-01
8	0	0	0	0	450	0	0	0	0	0	1970-01-01
9	0	0	0	0	1810	0	0	0	0	0	1970-01-01
10	0	0	0	0	0	2000	0	0	0	0	1970-01-01
11	0	0	0	0	0	0	1010	0	0	0	1970-01-01
12	0	0	0	0	0	0	0	1479	0	0	1970-01-01
13	0	0	0	0	0	0	0	0	20702	0	1970-01-01
14	0	0	0	0	0	0	0	0	30013	0	1970-01-01
15	0	0	0	0	0	0	0	0	5042	0	1970-01-01
16	0	0	0	0	0	0	0	0	5179	0	1970-01-01
17	0	0	0	0	0	0	0	0	34025	0	1970-01-01
18	0	0	0	0	0	0	0	0	0	1926	1970-01-01
19	0	0	0	0	0	0	0	0	0	3456	1970-01-01
20	0	0	0	0	0	0	0	0	0	2510	1970-01-01
21	0	0	0	0	0	0	0	0	0	1409	1970-01-01
22	0	0	0	0	0	0	0	0	0	468	1970-01-01
23	0	0	0	0	0	0	0	0	0	589	1970-01-01
24	0	0	0	0	0	0	0	0	0	1782	1970-01-01
25	0	0	0	0	0	0	0	0	0	804	1970-01-01
26	0	0	0	0	0	0	0	0	0	12813	1970-01-01
27	0	0	0	0	0	0	0	0	0	1559	1970-01-01
28	0	0	0	0	0	0	0	0	0	2072	1970-01-01
29	0	0	0	0	0	0	0	0	0	3456	1970-01-01
30	0	0	0	0	0	0	0	0	0	831	1970-01-01
31	0	0	0	0	0	0	0	0	0	2572	1970-01-01
32	0	0	0	0	0	0	0	0	0	1316	1970-01-01
33	0	0	0	0	0	0	0	0	0	13482	1970-01-01
34	0	0	0	0	0	0	0	0	0	1805	1970-01-01
35	0	0	0	0	0	0	0	0	0	954	1970-01-01
36	0	0	0	0	0	0	0	0	0	3456	1970-01-01
37	0	0	0	0	0	0	0	0	0	566	1970-01-01
38	0	0	0	0	0	0	0	0	0	3597	1970-01-01
39	0	0	0	0	0	0	0	0	0	697	1970-01-01
40	0	0	0	0	0	0	0	0	0	6490	1970-01-01
41	0	0	0	0	0	0	0	0	0	1277	1970-01-01
42	0	0	0	0	0	0	0	0	0	561	1970-01-01
43	1200	0	0	0	0	2000	0	0	0	0	1970-01-01
44	0	0	3400	2800	1300	0	0	0	3400	0	1970-01-01
45	0	0	0	0	0	0	0	0	0	2300	1970-01-01
46	0	0	0	0	0	0	0	1340	0	0	1970-01-01
47	0	0	0	0	0	0	0	0	0	0	1970-01-01
52	0	0	0	4000	0	0	0	0	0	0	1970-01-01
2	0	0	0	1650	0	0	0	0	0	0	1970-01-01
3	0	0	0	1395	0	0	0	0	0	0	1970-01-01
49	600	0	0	0	0	0	0	0	0	0	1970-01-01
50	700	0	0	0	0	0	0	0	0	0	1970-01-01
1	1900	0	0	0	0	0	0	0	0	0	1970-01-01
54	222	0	0	0	0	0	0	0	0	0	1970-01-01
55	0	0	0	444	0	0	0	0	0	0	1970-01-01
56	0	0	0	0	0	0	333	0	0	0	1970-01-01
57	0	0	0	0	0	0	0	0	21000	0	2024-03-12
58	333000	0	0	0	0	0	0	0	0	0	2024-02-02
\.


--
-- Data for Name: income_shop; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.income_shop (id, income_cashless, income_other, date_income, income_cash) FROM stdin;
14	0	0	2024-01-02	4300
16	0	0	2024-01-04	1850
17	0	0	2024-01-05	3600
18	0	0	2024-01-06	5700
19	0	0	2024-01-07	3850
20	0	0	2024-01-08	2200
21	0	0	2024-01-09	4300
22	0	0	2024-01-10	1450
23	0	0	2024-01-11	3150
24	0	0	2024-01-12	3000
25	0	0	2024-01-13	5100
26	0	0	2024-01-14	4250
27	0	0	2024-01-15	2100
28	0	0	2024-01-16	5750
29	0	0	2024-01-17	2450
30	0	0	2024-01-18	1500
31	0	0	2024-01-19	6700
32	0	0	2024-01-20	6925
33	0	0	2024-01-21	5800
34	0	0	2024-01-22	2450
35	0	0	2024-01-23	4550
36	0	0	2024-01-24	350
37	0	0	2024-01-25	4350
38	3186	0	2024-01-25	0
39	2548	0	2024-01-24	0
40	2314	0	2024-01-23	0
41	3050	0	2024-01-22	0
42	6105	0	2024-01-21	0
43	2528	0	2024-01-20	0
44	4771	0	2024-01-19	0
45	4149	0	2024-01-18	0
46	4729	0	2024-01-17	0
47	3393	0	2024-01-16	0
48	3768	0	2024-01-15	0
49	3932	0	2024-01-14	0
50	6346	0	2024-01-13	0
51	7611	0	2024-01-12	0
52	5190	0	2024-01-11	0
53	5516	0	2024-01-10	0
54	3137	0	2024-01-09	0
55	5465	0	2024-01-08	0
56	6772	0	2024-01-07	0
57	6877	0	2024-01-06	0
58	4656	0	2024-01-05	0
59	3097	0	2024-01-04	0
60	4305	0	2024-01-03	0
61	5242	0	2024-01-02	0
62	1380	0	2024-01-01	0
63	0	500	2024-01-01	0
64	2400	0	2024-03-05	1200
65	0	0	2024-03-04	4000
68	0	3000	2024-03-06	0
15	0	0	2024-03-06	4800
69	0	0	2024-03-05	2000
70	30000	0	2024-03-06	0
74	0	0	2024-03-06	45
73	0	0	2024-02-28	555555
76	0	0	2024-03-04	222
77	3333	0	2024-03-05	0
78	0	444	2024-03-06	0
80	0	0	2024-01-25	232323
81	23000	0	2024-03-06	0
\.


--
-- Data for Name: personal_expenses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.personal_expenses (id, food_expense, utility_expense, other_expense, date_expense_personal) FROM stdin;
133	0	567	0	2024-02-27
135	0	0	500	2024-02-26
137	333	0	0	2024-03-04
138	0	0	0	2024-03-05
139	444	0	0	2024-03-06
140	0	0	0	2024-03-05
141	0	300	0	2024-03-06
142	0	0	123	2024-02-27
143	0	0	321	2024-02-28
145	0	0	100	2024-03-11
\.


--
-- Name: business_expenses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.business_expenses_id_seq', 58, true);


--
-- Name: income_shop_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.income_shop_id_seq', 81, true);


--
-- Name: personal_expenses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personal_expenses_id_seq', 146, true);


--
-- Name: business_expenses business_expenses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.business_expenses
    ADD CONSTRAINT business_expenses_pkey PRIMARY KEY (id);


--
-- Name: income_shop income_shop_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.income_shop
    ADD CONSTRAINT income_shop_pkey PRIMARY KEY (id);


--
-- Name: personal_expenses personal_expenses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personal_expenses
    ADD CONSTRAINT personal_expenses_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

