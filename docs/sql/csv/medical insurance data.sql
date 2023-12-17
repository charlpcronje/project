
/*
create table ig_db.MedicalInsuranceCompany (
	MedicalInsuranceCompanyId int auto_increment primary key,
    Name varchar(500) not null,
    SchemeType varchar(255),
    PhoneNumber varchar(400),
    RegistrationDate DateTime, 
    LastUpdateTimestamp DateTime default current_timestamp,
    LastUpdateUserName varchar(255)
);

truncate table MedicalInsuranceCompany;

select * from MedicalInsuranceCompany;
*/

insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('AECI MEDICAL AID SOCIETY','Restricted','0860002103',str_to_date('11 Feb 1971', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('ALLIANCE-MIDMED MEDICAL SCHEME','Restricted','0128450080',str_to_date('30 Aug 1976', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('ANGLO MEDICAL SCHEME','Restricted','0860222633',str_to_date('16 Oct 1968', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('ANGLOVAAL GROUP MEDICAL SCHEME','Restricted','0860100693',str_to_date('28 Jul 1997', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('BANKMED','Restricted','08002265633',str_to_date('29 Jun 1972', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('BARLOWORLD MEDICAL SCHEME','Restricted','0860002106',str_to_date('01 Dec 1980', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('BESTMED MEDICAL SCHEME','Open','',str_to_date('11 Aug 1971', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('BMW EMPLOYEES MEDICAL AID SOCIETY','Restricted','0860002107',str_to_date('13 Jan 1984', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('BONITAS MEDICAL FUND','Open','0113845100',str_to_date('01 Mar 1982', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('BP MEDICAL AID SOCIETY','Restricted','',str_to_date('20 Feb 1968', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('BUILDING & CONSTRUCTION INDUSTRY MEDICAL AID FUND','Restricted','0112081000',str_to_date('02 Aug 2001', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('CAPE MEDICAL PLAN','Open','0219378300',str_to_date('11 Nov 1971', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('CHARTERED ACCOUNTANTS (SA) MEDICAL AID FUND (CAMAF)','Restricted','0861700600',str_to_date('08 Jun 1971', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('COMPCARE MEDICAL SCHEME','Open','0112081000',str_to_date('01 Jun 1978', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('DE BEERS BENEFIT SOCIETY','Restricted','0538073111',str_to_date('29 May 1969', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('DISCOVERY HEALTH MEDICAL SCHEME','Open','',str_to_date('08 Oct 1971', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('ENGEN MEDICAL BENEFIT FUND','Restricted','0800001615',str_to_date('07 Aug 1997', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('FEDHEALTH MEDICAL SCHEME','Open','0860002153',str_to_date('26 Nov 1969', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('FISHING INDUSTRY MEDICAL SCHEME (FISH-MED)','Restricted','0214029927',str_to_date('20 Oct 1967', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('FOODMED MEDICAL SCHEME','Restricted','0219303550',str_to_date('20 Oct 1967', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('GENESIS MEDICAL SCHEME','Open','0214429900',str_to_date('25 May 1995', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('GLENCORE MEDICAL SCHEME','Restricted','0860002141',str_to_date('07 Aug 1968', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('GOLDEN ARROWS EMPLOYEES MEDICAL BENEFIT FUND','Restricted','0860104122',str_to_date('30 Jun 1972', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('GOVERNMENT EMPLOYEES MEDICAL SCHEME (GEMS)','Restricted','0861114367',str_to_date('01 Jan 2005', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('HEALTH SQUARED MEDICAL SCHEME','Open','08617966400',str_to_date('23 Aug 1971', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('HORIZON MEDICAL SCHEME','Restricted','0860101103',str_to_date('11 Sep 1996', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('IMPALA MEDICAL PLAN','Restricted','0145694748',str_to_date('15 Jul 2002', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('IMPERIAL AND MOTUS MEDICAL AID','Restricted','0860105221',str_to_date('01 Dec 1995', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('KEYHEALTH','Open','0126672250',str_to_date('28 May 1968', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('LA-HEALTH MEDICAL SCHEME','Restricted','0219142103',str_to_date('10 Jan 1968', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('LIBCARE MEDICAL SCHEME','Restricted','0800122273',str_to_date('20 Feb 1969', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('LONMIN MEDICAL SCHEME','Restricted','0860104883',str_to_date('01 Jan 2006', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MAKOTI MEDICAL SCHEME','Open','0112081000',str_to_date('07 Sep 1976', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MALCOR MEDICAL SCHEME','Restricted','0860100698',str_to_date('18 May 1994', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MASSMART HEALTH PLAN','Restricted','0860002117',str_to_date('20 Oct 1978', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MBMED MEDICAL AID FUND','Restricted','0860002109',str_to_date('05 Dec 1969', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MEDIHELP','Open','0860100678',str_to_date('23 Jun 1969', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MEDIMED MEDICAL SCHEME','Open','0413954400',str_to_date('12 Sep 1980', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MEDIPOS MEDICAL SCHEME','Restricted','0860100078',str_to_date('15 Jun 1994', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MEDSHIELD MEDICAL SCHEME','Open','0105974701',str_to_date('06 Feb 1968', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MOMENTUM MEDICAL SCHEME','Open','0860117859',str_to_date('06 May 1971', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MOTOHEALTH CARE','Restricted','0861329800',str_to_date('01 Oct 2007', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('MULTICHOICE MEDICAL AID SCHEME','Restricted','0860116633',str_to_date('07 Mar 1972', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('NETCARE MEDICAL SCHEME','Restricted','0861638633',str_to_date('19 Dec 2000', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('OLD MUTUAL STAFF MEDICAL AID FUND','Restricted','0860100076',str_to_date('13 Feb 1969', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('PARMED MEDICAL AID SCHEME','Restricted','0860002126',str_to_date('29 Mar 1974', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('PG GROUP MEDICAL SCHEME','Restricted','0114175800',str_to_date('20 Nov 1970', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('PICK N PAY MEDICAL SCHEME','Restricted','0800004389',str_to_date('09 May 1996', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('PLATINUM HEALTH','Restricted','0874630660',str_to_date('19 Dec 2000', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('PROFMED','Restricted','0116288900',str_to_date('10 Aug 1969', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('RAND WATER MEDICAL SCHEME','Restricted','0116820985',str_to_date('24 Oct 1969', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('REMEDI MEDICAL AID SCHEME','Restricted','0860116116',str_to_date('18 Sep 1972', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('RETAIL MEDICAL SCHEME','Restricted','0860101252',str_to_date('10 Feb 1970', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('RHODES UNIVERSITY MEDICAL SCHEME','Restricted','0861727773',str_to_date('15 Dec 1967', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('SABC MEDICAL AID SCHEME','Restricted','0860002136',str_to_date('23 Jun 1972', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('SAMWUMED','Restricted','0216979000',str_to_date('11 Nov 1968', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('SASOLMED','Restricted','0860002134',str_to_date('17 Feb 1971', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('SEDMED','Restricted','',str_to_date('19 Feb 1987', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('SISONKE HEALTH MEDICAL SCHEME','Restricted','0413954400',str_to_date('15 Jan 1997', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('SIZWE HOSMED MEDICAL SCHEME','Open','0112981500',str_to_date('17 Mar 1978', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('SOUTH AFRICAN BREWERIES MEDICAL SCHEME','Restricted','0860002133',str_to_date('01 Sep 1970', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('SOUTH AFRICAN POLICE SERVICE MEDICAL SCHEME (POLMED)','Restricted','0128187500',str_to_date('01 Nov 1999', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('SUREMED HEALTH','Open','0860080888',str_to_date('20 Aug 1976', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('TFG MEDICAL AID SCHEME','Restricted','0215271159',str_to_date('18 Nov 1998', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('THEBEMED','Open','0115448899',str_to_date('12 Sep 2002', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('TIGER BRANDS MEDICAL SCHEME','Restricted','0112081000',str_to_date('01 Jun 1993', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('TRANSMED MEDICAL FUND','Restricted','0800450010',str_to_date('22 Nov 2000', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('TSOGO SUN GROUP MEDICAL SCHEME','Restricted','0860100421',str_to_date('30 Jul 1999', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('UMVUZO HEALTH MEDICAL SCHEME','Restricted','0128450000',str_to_date('01 Jul 2004', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('UNIVERSITY OF KWA-ZULU NATAL MEDICAL SCHEME','Restricted','0860113322',str_to_date('01 Jul 1983', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('WITBANK COALFIELDS MEDICAL AID SCHEME','Restricted','0136561407',str_to_date('30 Apr 1969', '%d %M %Y %r'),current_timestamp,'admin');
insert into MedicalInsuranceCompany (Name, SchemeType, PhoneNumber, RegistrationDate, LastUpdateTimestamp, LastUpdateUserName) values ('WOOLTRU HEALTHCARE FUND','Restricted','0802228922',str_to_date('12 Dec 1969', '%d %M %Y %r'),current_timestamp,'admin');



create table ig_db.MainMemberMedicalInsurance (
	MainMemberMedicalInsuranceId int auto_increment primary key,
    IndividualId int,
    MedicalInsuranceCompanyId int,
    MedicalInsurancePlanId int,
    InsuranceNumber varchar(50),
    Description varchar(255),
    LastUpdateTimestamp datetime default current_timestamp,
    LastUpdateUserName varchar(50)
);

create table ig_db.MedicalDependant (
	MedicalDependantId int auto_increment primary key,
    MainMemberMedicalInsuranceId int,
    IndividualIdDependant int,
    Description varchar(255),
    LastUpdateTimestamp datetime default current_timestamp,
    LastUpdateUserName varchar(50)
);
