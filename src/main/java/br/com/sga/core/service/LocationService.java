package br.com.sga.core.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.sga.core.exception.NotFoundException;
import br.com.sga.core.model.Location;
import br.com.sga.core.repository.jpa.LocationRepository;
import br.com.sga.core.repository.jpa.specification.LocationSpecification;
import br.com.sga.core.util.SpecificationUtil;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	public Page<Location> findAll(Pageable pageable){
		return locationRepository.findAll(pageable);
	}
	
	public List<Location> findAll(Location locationFilter){
		Specification<Location> specification = geraSpecification(locationFilter);
		
		return locationRepository.findAll(specification);
	}
	
	public Page<Location> findAll(Location locationFilter, Pageable pageable){
		Specification<Location> specification = geraSpecification(locationFilter);
		
		return locationRepository.findAll(specification, pageable);
	}
	
	public Location findById(Long id) {
		return locationRepository.findById(id).orElseThrow(() -> new NotFoundException("Location não encontrado"));
	}
	
	public Location save(Location region) {
		return locationRepository.save(region);
	}
	
	public void delete(Location region) {
		locationRepository.delete(region);
	}
	
	public InputStream generateLocationExcel(Location locationFilter) throws IOException{

		List<Location> locations = this.findAll(locationFilter);
		
		// local do arquivo
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet =  workbook.createSheet("Resultado"); 
		CellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);
		
		//cabeçalho
		String[] nomesColunas = new String[] {"Endereço", "CEP", "Cidade", "Estado", "Country", "Region"};
		HSSFRow rowhead=   sheet.createRow((short)0);
		for(int i=0; i < nomesColunas.length; i++) {
			sheet.setColumnWidth(i, 20 * 256);
			rowhead.createCell(i).setCellValue(nomesColunas[i]);
			rowhead.getCell(i).setCellStyle(style);
		}
		
		//dados
		for(int i=0; i < locations.size(); i++) {
			HSSFRow row = sheet.createRow((short) i+1);
			
			sheet.setColumnWidth(0, 20 * 256);
			row.createCell(0).setCellValue(locations.get(i).getStreetAddress());
			
			sheet.setColumnWidth(1, 20 * 256);
			row.createCell(1).setCellValue(locations.get(i).getPostalCode());
			
			sheet.setColumnWidth(2, 20 * 256);
			row.createCell(2).setCellValue(locations.get(i).getCity());
			
			sheet.setColumnWidth(3, 20 * 256);
			row.createCell(3).setCellValue(locations.get(i).getStateProvince());
			
			sheet.setColumnWidth(4, 20 * 256);
			row.createCell(4).setCellValue(locations.get(i).getStateProvince());
			
			sheet.setColumnWidth(5, 20 * 256);
			row.createCell(5).setCellValue(locations.get(i).getStateProvince());
			
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		byte[] barray = bos.toByteArray();
		InputStream is = new ByteArrayInputStream(barray);

		workbook.close();

		return is;
	}

	public InputStream generateLocationPdf(Location locationFilter) throws DocumentException {

		List<Location> locations = this.findAll(locationFilter);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		Document document = new Document();
		
		PdfPTable table = new PdfPTable(new float[] { 3, 2, 2, 2, 2, 2 });
		
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		
		table.addCell("Endereço");
		table.addCell("CEP");
		table.addCell("Cidade");
		table.addCell("Estado");
		table.addCell("Country");
		table.addCell("Region");
		
		table.setHeaderRows(1);
		PdfPCell[] cells = table.getRow(0).getCells();
		for (int j = 0; j < cells.length; j++) {
			cells[j].setBackgroundColor(BaseColor.GRAY);
		}
		
		for(int i=0; i < locations.size(); i++) {
			table.addCell(locations.get(i).getStreetAddress());
			table.addCell(locations.get(i).getPostalCode());
			table.addCell(locations.get(i).getCity());
			table.addCell(locations.get(i).getStateProvince());
			table.addCell(locations.get(i).getCountry().getName());
			table.addCell(locations.get(i).getCountry().getRegion().getName());
		}
		
//		for (int i = 1; i < 5; i++) {
//			table.addCell("Endereço no Brasil");
//			table.addCell("74000-000");
//			table.addCell("Goiânia");
//			table.addCell("Goiás");
//			table.addCell("Brasil");
//			table.addCell("America");
//		}
		PdfWriter.getInstance(document, out);
		document.open();
		document.add(table);
		document.close();
		
		return new ByteArrayInputStream(out.toByteArray());
	}

	@SuppressWarnings("unchecked")
	private Specification<Location> geraSpecification(Location locationFilter) {
		
		Specification<Location> specification = null;
		
		if(StringUtils.isNotEmpty(locationFilter.getStreetAddress())) {
			specification = SpecificationUtil.addClausulaAnd(specification, LocationSpecification.whereByStreetName(locationFilter.getStreetAddress()));
		}
		
		if(StringUtils.isNotEmpty(locationFilter.getPostalCode())) {
			specification = SpecificationUtil.addClausulaAnd(specification, LocationSpecification.whereByPostalCode(locationFilter.getPostalCode()));
		}
		
		if(StringUtils.isNotEmpty(locationFilter.getCity())) {
			specification = SpecificationUtil.addClausulaAnd(specification, LocationSpecification.whereByCity(locationFilter.getCity()));
		}
		
		if(StringUtils.isNotEmpty(locationFilter.getStateProvince())) {
			specification = SpecificationUtil.addClausulaAnd(specification, LocationSpecification.whereByStateProvince(locationFilter.getStateProvince()));
		}

		if(locationFilter.getCountry() != null && locationFilter.getCountry().getId() != null) {
			specification = SpecificationUtil.addClausulaAnd(specification, LocationSpecification.whereByCountryId(locationFilter.getCountry().getId()));
		}

		if(locationFilter.getCountry() != null && locationFilter.getCountry().getRegion() != null && locationFilter.getCountry().getRegion().getId() != null) {
			specification = SpecificationUtil.addClausulaAnd(specification, LocationSpecification.whereByRegionId(locationFilter.getCountry().getId()));
		}
		
		return specification;
	}
}
