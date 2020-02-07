accept(Transaction) :-
	extract(Transaction, format, auctionHouse2019),

	extract(Transaction, cert, Cert),
	extract(Cert, format, x509cert),

	% did the cert really sign the transaction?
	extract(Cert, pubKey, PK),
	verify_signature(Form, PK),

	extract(Cert, issuer, IssuerCert),
	extract(IssuerCert, trustscheme, TrustschemeMembershipClaim),

	trustlist(TrustschemeMembershipClaim, IssuerCertificate, TrustListEntry),
	trustschemeX(TrustschemeMembershipClaim, azTrustscheme, TrustListEntry,	TrustedTrustListEntry),

	% some issuer checks based on AZ tuples:
	extract(TrustedTrustListEntry, serviceType, eIDqualified),
	extract(TrustedTrustListEntry, signedInPerson, true),

	extract(TrustedTrustListEntry, pubKey, PkIss),
	verify_signature(Certificate, PkIss).


trustschemeX(Claim, TrustedScheme, TrustListEntry, TrustListEntry) :-
	% true iff Claim == gov.az
	trustscheme(Claim, TrustedScheme).
	% no need to translate => input param = output param (?)


trustschemeX(Claim, TrustedScheme, TrustListEntry, TrustedTrustListEntry) :-
	% Claim = trust.eu
	% TrustedScheme = gov.az
	% TTAdomain = trust.eu._translations._trust.goz.az
	encodeX(Claim, TrustedScheme, TTAdomain),
	lookup(TTAdomain, TranslationEntry),

	% TranslationEntry = translation table in e.g. XML
	translate(TranslationEntry, TrustListEntry, TrustedTrustListEntry).
