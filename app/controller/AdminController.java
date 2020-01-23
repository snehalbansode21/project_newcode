package com.app.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.pojos.Address;
import com.app.pojos.Event;
import com.app.pojos.EventDesc;
import com.app.pojos.Food;
import com.app.pojos.FoodSubMenu;
import com.app.pojos.Location;
import com.app.pojos.Manager;
import com.app.pojos.User;
import com.app.pojos.UserRole;
import com.app.pojos.VenueCity;
import com.app.service.IAdminService;
import com.app.service.IClientService;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private IAdminService adminService;
	@Autowired
	private IClientService clientService;
	
	@PostConstruct
	public void myInit() {
		System.out.println("in init " + adminService);
	}
	
	@GetMapping("/listusers")
	public ResponseEntity<?> listUsers()
	{
		System.out.println("in list users ");
		try {
			List<User> userList = adminService.listUsers();
			if(userList.size() == 0)
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/listevents")
	public ResponseEntity<?> listEvents()
	{
		System.out.println("in list events..");
		try {
			List<Event> eventList = adminService.listEvents();
			if(eventList.size() == 0)
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<Event>>(eventList, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/listvenuecity")
	public ResponseEntity<?> listVenueCity()
	{
		System.out.println("in list venue city");
		try {
			List<VenueCity> venueCityList = adminService.listVenueCity();
			if(venueCityList.size() == 0)
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<VenueCity>>(venueCityList, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/listlocation")
	public ResponseEntity<?> listLocation()
	{
		System.out.println("in list location");
		try {
			List<Location> locationList = adminService.listLocation();
			if(locationList.size() == 0)
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<Location>>(locationList, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/listfoodtype")
	public ResponseEntity<?> listFoodType()
	{
		System.out.println("in list food type");
		try {
			List<Food> foodTypeList = adminService.listFoodType();
			if(foodTypeList.size() == 0)
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<Food>>(foodTypeList, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/listfoodsubmenu")
	public ResponseEntity<?> listFoodSubMenu()
	{
		System.out.println("in list food sub menu");
		try {
			List<FoodSubMenu> foodSubMenuList = adminService.listFoodSubMenu();
			if(foodSubMenuList.size() == 0)
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<List<FoodSubMenu>>(foodSubMenuList, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/inserteventdesc/{mgr_id}")
	public ResponseEntity<?> insertEventDesc(@RequestBody EventDesc ed,@PathVariable int mgr_id)
	{
		System.out.println("in insert event desc.."+ed);
		Manager mgr = adminService.getManagerById(mgr_id);
		mgr.addEventDesc(ed);
		try {
			return new ResponseEntity<EventDesc>(adminService.insertEventDesc(ed), HttpStatus.OK);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@PostMapping("/addmanager")
	public ResponseEntity<?> addManager(@RequestBody User mgr)
	{
		System.out.println("in add manager "+ mgr);
		try {
			Manager m = new Manager();
			String role = "MANAGER";
			mgr.setRole(UserRole.valueOf(role));
			mgr.addManager(m);
			return new ResponseEntity<User>(adminService.addManager(mgr), HttpStatus.OK);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/addaddressofmanager/{user_id}")
	public ResponseEntity<?> addAddressOfManager(@RequestBody Address addr,@PathVariable int user_id)
	{

		System.out.println("in add address of manager" + addr);
		System.out.println("user id" +user_id);
		try {
			User user = clientService.getUserById(user_id);
			clientService.addAddress(addr);
			user.addAddress(addr);
			return new ResponseEntity<User>(clientService.updateClient(user), HttpStatus.OK);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/insertvenuecity")
	public ResponseEntity<?> insertVenueCity(@RequestBody VenueCity vc)
	{
		System.out.println("in insert venue city");
		try {
			if(vc != null)
				return new ResponseEntity<VenueCity>(adminService.insertVenueCity(vc), HttpStatus.OK);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/insertlocation/{venueCity_id}")
	public ResponseEntity<?> insertLocation(@RequestBody Location loc,@PathVariable int venueCity_id )
	{
		System.out.println("in insert location");
		try {
			VenueCity vc = adminService.getVenueCityById(venueCity_id);
			vc.addLocation(loc);
			if(loc != null)
				return new ResponseEntity<Location>(adminService.insertLocation(loc), HttpStatus.OK);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/insertfood/{event_id}")
	public ResponseEntity<?> insertFoodType(@RequestBody Food food,@PathVariable int event_id)
	{
		System.out.println("in insert food type");
		try {
			Event event = adminService.getEventById(event_id);
			event.addFood(food);
			if(food != null)
				return new ResponseEntity<Food>(adminService.insertFood(food), HttpStatus.OK);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/insertfoodsubmenu/{foodType_id}")
	public ResponseEntity<?> insertFoodSubMenu(@RequestBody FoodSubMenu foodsubmenu,@PathVariable int foodType_id)
	{
		System.out.println("in insert food submenu");
		try {
			Food food = adminService.getFoodTypeById(foodType_id);
			food.addFoodSubMenu(foodsubmenu);
			if(foodsubmenu != null)
				return new ResponseEntity<FoodSubMenu>(adminService.insertFoodSubMenu(foodsubmenu), HttpStatus.OK);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
