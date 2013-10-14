create table deleted_goods (
  id_deleted_goods          integer auto_increment not null,
  goods_id_goods            integer,
  quantity                  integer,
  stock_quantity            integer,
  timestamp                 datetime,
  information               TEXT,
  constraint pk_deleted_goods primary key (id_deleted_goods))
;

create table goods (
  id_goods                  integer auto_increment not null,
  insurance_id_insurance    integer,
  name                      varchar(255),
  description               TEXT,
  type                      varchar(14),
  unit                      varchar(255),
  current_stock             integer,
  information               TEXT,
  goods_package             varchar(255),
  category                  varchar(7),
  minimum_stock             integer,
  is_important              tinyint(1) default 0,
  constraint ck_goods_type check (type in ('BMHP','OBAT','PSIKOTROPIKA','ALAT_KESEHATAN','NARKOTIKA')),
  constraint ck_goods_category check (category in ('GENERIK','PATEN')),
  constraint pk_goods primary key (id_goods))
;

create table goods_consumption (
  id_goods_consumption      integer auto_increment not null,
  goods_id_goods            integer,
  quantity                  integer,
  timestamp                 datetime,
  stock_quantity            integer,
  information               TEXT,
  constraint pk_goods_consumption primary key (id_goods_consumption))
;

create table goods_receipt (
  id_goods_receipt          integer auto_increment not null,
  invoice_id_invoice        integer,
  goods_id_goods            integer,
  quantity_received         integer,
  information               TEXT,
  date                      datetime,
  expired_date              datetime,
  timestamp                 datetime,
  stock_quantity            integer,
  constraint pk_goods_receipt primary key (id_goods_receipt))
;

create table insurance (
  id_insurance              integer auto_increment not null,
  name                      varchar(255),
  description               TEXT,
  constraint pk_insurance primary key (id_insurance))
;

create table invoice (
  id_invoice                integer auto_increment not null,
  invoice_number            varchar(255),
  timestamp                 datetime,
  amoount_paid              integer,
  constraint pk_invoice primary key (id_invoice))
;

create table invoice_item (
  id_invoice_item           integer auto_increment not null,
  invoice_id_invoice        integer,
  purchase_order_item_id_purchase_order_item integer,
  batch                     varchar(255),
  discount                  float,
  price                     integer,
  quantity                  integer,
  constraint pk_invoice_item primary key (id_invoice_item))
;

create table purchase_order (
  id_purchase_order         integer auto_increment not null,
  purchase_order_number     integer,
  rayon                     varchar(255),
  purchase_order_type       varchar(12),
  constraint ck_purchase_order_purchase_order_type check (purchase_order_type in ('PSIKOTROPIKA','NARKOTIKA')),
  constraint pk_purchase_order primary key (id_purchase_order))
;

create table purchase_order_item (
  id_purchase_order_item    integer auto_increment not null,
  supplier_goods_id_supplier_goods integer,
  purchase_order_id_purchase_order integer,
  date                      datetime,
  quantity                  integer,
  information               TEXT,
  timestamp                 datetime,
  constraint pk_purchase_order_item primary key (id_purchase_order_item))
;

create table req_planning (
  id_req_planning           integer auto_increment not null,
  supplier_goods_id_supplier_goods integer,
  period                    datetime,
  quantity                  integer,
  accepted_quantity         integer,
  is_accepted               tinyint(1) default 0,
  information               TEXT,
  timestamp                 datetime,
  price_estimation          integer,
  constraint pk_req_planning primary key (id_req_planning))
;

create table role (
  id_role                   integer auto_increment not null,
  role_name                 varchar(255),
  description               varchar(255),
  code                      varchar(255),
  constraint pk_role primary key (id_role))
;

create table setting (
  id_setting                integer auto_increment not null,
  setting_name              varchar(255),
  setting_description       varchar(255),
  setting_key               varchar(255),
  setting_value             varchar(255),
  constraint pk_setting primary key (id_setting))
;

create table supplier (
  id_supplier               integer auto_increment not null,
  supplier_abbr             varchar(255),
  supplier_name             varchar(255),
  supplier_address          varchar(255),
  phone_number              varchar(255),
  email                     varchar(255),
  constraint pk_supplier primary key (id_supplier))
;

create table supplier_goods (
  id_supplier_goods         integer auto_increment not null,
  supplier_id_supplier      integer,
  goods_id_goods            integer,
  last_price                integer,
  manufacturer              varchar(255),
  last_update               datetime,
  constraint pk_supplier_goods primary key (id_supplier_goods))
;

create table user (
  id_user                   integer auto_increment not null,
  role_id_role              integer,
  employee_num              varchar(255),
  name                      varchar(255),
  place_birth               varchar(255),
  date_of_birth             datetime,
  phone_number              varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (id_user))
;

alter table deleted_goods add constraint fk_deleted_goods_goods_1 foreign key (goods_id_goods) references goods (id_goods) on delete restrict on update restrict;
create index ix_deleted_goods_goods_1 on deleted_goods (goods_id_goods);
alter table goods add constraint fk_goods_insurance_2 foreign key (insurance_id_insurance) references insurance (id_insurance) on delete restrict on update restrict;
create index ix_goods_insurance_2 on goods (insurance_id_insurance);
alter table goods_consumption add constraint fk_goods_consumption_goods_3 foreign key (goods_id_goods) references goods (id_goods) on delete restrict on update restrict;
create index ix_goods_consumption_goods_3 on goods_consumption (goods_id_goods);
alter table goods_receipt add constraint fk_goods_receipt_invoice_4 foreign key (invoice_id_invoice) references invoice (id_invoice) on delete restrict on update restrict;
create index ix_goods_receipt_invoice_4 on goods_receipt (invoice_id_invoice);
alter table goods_receipt add constraint fk_goods_receipt_goods_5 foreign key (goods_id_goods) references goods (id_goods) on delete restrict on update restrict;
create index ix_goods_receipt_goods_5 on goods_receipt (goods_id_goods);
alter table invoice_item add constraint fk_invoice_item_invoice_6 foreign key (invoice_id_invoice) references invoice (id_invoice) on delete restrict on update restrict;
create index ix_invoice_item_invoice_6 on invoice_item (invoice_id_invoice);
alter table invoice_item add constraint fk_invoice_item_purchaseOrderI_7 foreign key (purchase_order_item_id_purchase_order_item) references purchase_order_item (id_purchase_order_item) on delete restrict on update restrict;
create index ix_invoice_item_purchaseOrderI_7 on invoice_item (purchase_order_item_id_purchase_order_item);
alter table purchase_order_item add constraint fk_purchase_order_item_supplie_8 foreign key (supplier_goods_id_supplier_goods) references supplier_goods (id_supplier_goods) on delete restrict on update restrict;
create index ix_purchase_order_item_supplie_8 on purchase_order_item (supplier_goods_id_supplier_goods);
alter table purchase_order_item add constraint fk_purchase_order_item_purchas_9 foreign key (purchase_order_id_purchase_order) references purchase_order (id_purchase_order) on delete restrict on update restrict;
create index ix_purchase_order_item_purchas_9 on purchase_order_item (purchase_order_id_purchase_order);
alter table req_planning add constraint fk_req_planning_supplierGoods_10 foreign key (supplier_goods_id_supplier_goods) references supplier_goods (id_supplier_goods) on delete restrict on update restrict;
create index ix_req_planning_supplierGoods_10 on req_planning (supplier_goods_id_supplier_goods);
alter table supplier_goods add constraint fk_supplier_goods_supplier_11 foreign key (supplier_id_supplier) references supplier (id_supplier) on delete restrict on update restrict;
create index ix_supplier_goods_supplier_11 on supplier_goods (supplier_id_supplier);
alter table supplier_goods add constraint fk_supplier_goods_goods_12 foreign key (goods_id_goods) references goods (id_goods) on delete restrict on update restrict;
create index ix_supplier_goods_goods_12 on supplier_goods (goods_id_goods);
alter table user add constraint fk_user_role_13 foreign key (role_id_role) references role (id_role) on delete restrict on update restrict;
create index ix_user_role_13 on user (role_id_role);


