package edu.retrofit.API.controller;

import edu.retrofit.API.DAO.DAOService;
import edu.retrofit.API.helpers.WebServiceInterface;

//delegar ou buscar quem vai trazer o serviço
public class ServiceController implements WebServiceInterface{
	private DAOService daoService = new DAOService(this);
	private WebServiceInterface listener;

	public ServiceController(WebServiceInterface listener) {
		this.listener = listener;
	}

	public void callService(String categoria, String idioma, int pag, String regiao){
		daoService.callService(categoria, idioma, pag, regiao);
	}

	@Override
	public void success(Object obj) {
		listener.success(obj);
	}

	@Override
	public void erro(Throwable throwable) {
		listener.erro(throwable);
	}
}
