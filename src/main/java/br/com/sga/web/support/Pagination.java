package br.com.sga.web.support;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class Pagination<T> {
	//Pagination Page Model
	public static final String PAGE_MODEL_NAME		= "page";
	
	//PageRequest
	public static final int PAGE_START 				= 1;
	public static final int PAGE_MAX_SIZE 			= 100;
	
	
	//Pagination Info
	public static String PAGINATION_MESSAGE_INFO = "Exibindo página %s de %s. Total: %s itens";

	public static final int MAX_PAGE_ITEM_DISPLAY = 5;
	
	private int currentNumber;
	
	private Page<T> page;
	private List<PageItem> items;
	private UriComponentsBuilder uriBuilder;
	
	public Pagination(Page<T> page, HttpServletRequest request) {
		
		UriComponents uri = inboundSpaceBugFix(request);
		
		this.page = page != null ? page : new PageImpl<>(new ArrayList<>());
		this.uriBuilder = ServletUriComponentsBuilder.fromUri(uri.toUri());
		this.items = new ArrayList<>();
		this.currentNumber = this.page.getNumber() + PAGE_START;

		int start;
		int size;

		int totalPages = this.page.getTotalPages();
		
		if (totalPages <= MAX_PAGE_ITEM_DISPLAY) {
			start = 1;
			size = totalPages;
		} else {
			if (this.currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY / 2) {
				start = 1;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else if (this.currentNumber >= totalPages - MAX_PAGE_ITEM_DISPLAY / 2) {
				start = totalPages - MAX_PAGE_ITEM_DISPLAY + 1;
				size = MAX_PAGE_ITEM_DISPLAY;
			} else {
				start = this.currentNumber - MAX_PAGE_ITEM_DISPLAY / 2;
				size = MAX_PAGE_ITEM_DISPLAY;
			}
		}

		for (int i = 0; i < size; i++) {
			items.add(new PageItem(start + i, (start + i) == this.currentNumber));
		}
	}
	
	public boolean isEmpty() {
		return page.getContent().isEmpty();
	}

	public String urlToPage() {
		return urlToPage(PAGE_START);
	}
	
	public String urlToPage(int page) {
		UriComponentsBuilder builder = uriBuilder.replaceQueryParam("page", page);
		return outboundSpaceBugFix(builder);
	}
	
	public String urlToPageSize(int size) {
		
		UriComponentsBuilder uriBuilderSize = ServletUriComponentsBuilder.fromUriString(uriBuilder.build(true).encode().toUriString());
		
		uriBuilderSize.replaceQueryParam("size", size);
		uriBuilderSize.replaceQueryParam("page", PAGE_START);
		
		return outboundSpaceBugFix(uriBuilderSize);
	}
	
	public List<PageItem> getItems() {
		return items;
	}

	public int getNumber() {
		return currentNumber;
	}

	public List<T> getContent() {
		return page.getContent();
	}

	public int getSize() {
		return page.getSize();
	}

	public int getTotalPages() {
		return page.getTotalPages();
	}
	
	public long getTotalElements() {
		return page.getTotalElements();
	}
	
	public long getNumberOfElements(){
		return page.getNumberOfElements();
	}
	
	public long getPageStart() {
		return PAGE_START;
	}
	
	public String getPageInfo(){
		return String.format(PAGINATION_MESSAGE_INFO, getNumber(), getTotalPages(), getTotalElements());
	}

	public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}

	public boolean isPrevious() {
		return page.hasPrevious();
	}

	public boolean isNext() {
		return page.hasNext();
	}

	private String outboundSpaceBugFix(UriComponentsBuilder builder){
		return builder.build(true).encode().toUriString();
	}
	
	private UriComponents inboundSpaceBugFix(HttpServletRequest request){

		/**
		 * Spring FrameworkSPR-10172
		 * UriComponents.Type.QUERY_PARAM does not match spec
		 * https://jira.spring.io/browse/SPR-10172
		 */
		
		ServletUriComponentsBuilder uriComponentsBuilder = ServletUriComponentsBuilder.fromRequest(request);
		
		String queryString = request.getQueryString();
		
		if (queryString != null) {
			uriComponentsBuilder.replaceQuery(request.getQueryString().replace("+",  "%20"));
		}
		
		return uriComponentsBuilder.build(true);
	}
	
	public class PageItem {
		private int number;
		private boolean current;

		public PageItem(int number, boolean current) {
			this.number = number;
			this.current = current;
		}

		public int getNumber() {
			return this.number;
		}

		public boolean isCurrent() {
			return this.current;
		}
	}
}