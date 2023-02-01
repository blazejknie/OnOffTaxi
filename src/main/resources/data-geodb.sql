INSERT INTO Users VALUES (1,'mastah','$2a$14$yD9oysrdchqpoQ/3Ouu/G.NLwpB9eSXjINe.AnwRs24Fx.9lIwQfa','ADMIN',FALSE),	-- pass: coinmaster
(2,'fuxy','$2a$14$XNo0N7tydS7YaIwldVB2RejqPs0GUwJHKj1KDw/.fth2cDycj0WPC','DRIVER',FALSE),	-- drv1
(3,'zjhw','$2a$14$Hnas3fSSFA2VEcvFIvqKVOo9r/NQKKzeilJPoSfTZPiE50j7PXtBK','CLIENT',FALSE),	-- cli1
(4,'rwyf','$2a$14$5wRZBWXmtwaXq.8xbz/E6.qiwMnWqSjv4nXiaqKcNZ1/L1n3ix3cC','DRIVER',FALSE),	-- drv2
(5,'ggqk','$2a$14$dg6rra7qKqlzJPl5slbDgegvdfUjXbCD3rOtZYxIBhE7VRicz02Yy','DRIVER',FALSE),	-- drv3
(6,'zldm','$2a$14$sU.47nbnnVjwW43ftMei8uPP25DL297X13UyjUarzoQ4YlDP84ZMu','CLIENT',FALSE),	-- cli2
(7,'vovd','invalidhash1','GUEST',FALSE),
(8,'testah','$2a$14$PSuSuCSBo7uNYxkC3zJ3nuASf8eIMj.m0ieeG91dbFmeVAcheo/m6','ADMIN',FALSE),	-- asdfmin
(9,'prcp','$2a$14$rPcX8Kh6vX8wI2VSFObUT.X6B327nz2ujOKgl.FHtnMKf6oH2eNTS','DRIVER',FALSE),	-- drv4
(10,'krbj','invalidhash2','GUEST',FALSE),
(11,'trsv','$2a$14$MxP51ZcS4X9BdAe.jc1TUuEEIr.fWhkMWoyl0/PhSYjhh/wC1Mp3i','DRIVER',FALSE),	-- drv5
(12,'njsd','$2a$14$N2nLeEKt/s3z2lxIkfNzoe2x/yvWkzzu0f76IMOn0qoaDeBunoKTa','DRIVER',FALSE);	-- drv6

INSERT INTO Drivers VALUES
(2, 'Ninomysł','Krystianczuk','Whats1963@rhyta.com','433453700','Prywatna korporacja Złotówa','2895642412','545812930','ul. Fieldorfa Emila (Generała Emila Fieldorfa)','Mełchów','65841','12/2018','https://picsum.photos/id/611/200/300','zlotowa','Stopą cieniem u miał w tej sprawie stropem hali, trzymając foteli',FALSE,1),
(4, 'Emeryk','Matałek','Paluncelow74@cuvox.de','899442591','Jan Kowalski Przewóz Osób','7842157599','051207718','ul. Jagiellońska','Chrusty','51226','41/2002',NULL,'emat','Zasypany gdy ona zakręcała, unosił rozpoczęła się w nim komisji z dowodem w ręku',FALSE,2),
(5, 'Marcjal','Gehfelt','Hadlady49@einrot.com','357007422','FHU Różowy Ocean Nad Brzegiem Morza','4724215199','48134755562719','ul. Mazowiecka','Leszczków','87422','123/2018','https://picsum.photos/id/1005/5760/3840','FHU RONBM','A on ręce do tyłu i stając Łączki wyrwał go światłem. Podługowate, jednak jakoś wracała może okrętu.',TRUE,0),
(9, 'Salomon','Solar','Sciespoins@einrot.com','953410550','Działalność Gospodarcza Salomona Solara','39655243310','49605606829285','ul. Kard. Wyszyńskiego Stefana','Ścichawa','98423','854/1999','https://picsum.photos/id/76/200/300','Ścichtax','Olej ścina rurze, żeby brzmiące jak formuły i zaklęcia bokach cztery rękojeści główne to tylko żart.',TRUE,2),
(11, 'Zbisława','Dejmkowska','Alkees1947@armyspy.com','619053927','Best Taxi Warszawa sp. z o.o.','6857256769','57550151200741','ul. Szmidta Andrzeja','Rzeszów','12425','1/2019','','DEJMKOWSKA','Pojawią, nie będzie kalkulatorów i astrografu oraz  najświętszy płynny, że jak gdyby śniony wstęgowego. W szydercza niż malownicza! Zadanie powtórzył, nie będzie już trzeba!',FALSE,1),
(12, 'Patrycjusz','Notorski','Ongrat1965@dayrep.com','482450775','Usługi Transportowo-Przewozowe "Notorex"','7459623485','415790695','ul. Jeżynowa','Wilczyska','01872','78/2010','https://picsum.photos/id/70/400/400','Notorex','Następne, jeszcze ciemniejsze pomieszczenie. Można strzelać pożaru!',TRUE,0);

INSERT INTO Clients VALUES
(3, 'Zachariasz','Ołado','Agron1934@gustr.com',168987514),
(6, 'Karina','Ciurus','Unth1972@fleckens.hu',856950459);

INSERT INTO Services VALUES (1, 2, 'FLY_ME_TO_THE_MOON', '20.00'),
(2, 2, 'SHOPPING', '10.00'),
(3, 4, 'SUPPLY_GAS', '15.00');

INSERT INTO Places(name, district, location, driver_id) VALUES
('Kościerzyna','kościerski',ST_GeomFromText('POINT(17.8260328 54.0973286)',0),2),    -- gmina
('Kościerzyna','kościerski',ST_GeomFromText('POINT(17.9671354 54.1204888)',0),4),
('Bytów','bytowski',ST_GeomFromText('POINT(17.4655971 54.1799035)',0),5),
('Żukowo','kartuski',ST_GeomFromText('POINT(18.2705887 54.3388657)',0),9),
('Kartuzy','kartuski',ST_GeomFromText('POINT(18.1785754 54.3363637)',0),11),
('Kościerzyna','kościerski',ST_GeomFromText('POINT(17.9671354 54.1204888)',0),12);

INSERT INTO Geolocations(position, driver_id) VALUES
(ST_GeomFromText('POINT(18.0018924 54.1278265)',0),2),
(ST_GeomFromText('POINT(17.8880053 54.0746272)',0),4),
(ST_GeomFromText('POINT(17.4655971 54.1799035)',0),5),
(ST_GeomFromText('POINT(18.3444031 54.3393544)',0),9),
(ST_GeomFromText('POINT(18.3444031 54.3393544)',0),11),
(ST_GeomFromText('POINT(18.3444031 54.3393544)',0),12);

INSERT INTO Ads VALUES
(1,'','https://files.slack.com/files-pri/TJ1DNHXN3-FM23N2VPF/sterfa.jpg','https://www.facebook.com/strefablysku/','alternativetext',''),
(2,'','https://www.ceneria.pl/Upload/image/2018/16726.jpg','https://www.skalnik.pl/','alter',''),
(3,'','https://i.ytimg.com/vi/cUT1zX_nnKY/maxresdefault.jpg','https://www.tvp.pl/','','');
