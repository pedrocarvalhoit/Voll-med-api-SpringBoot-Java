alter table pacientes add status tinyint(20);
update pacientes set status = 1;