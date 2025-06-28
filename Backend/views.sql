create materialized view accommodations_per_host as
select h.id        as host_id,
       count(a.id) as num_accommodations
from accommodation a
         left join host h on a.host_id = h.id
group by h.id;

create materialized view hosts_per_country as
select c.id        as country_id,
       count(h.id) as num_hosts
from host h
         left join country c on h.country_id = c.id
group by c.id;