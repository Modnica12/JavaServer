package com.faceless.containers;

import com.faceless.Application;
import com.faceless.sql.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VirtualMachine
{
	public String vm_name   = null,
			cpu_vendor      = null,
			cpu_frequency   = null,
			cpu_core_count  = null,
			ram_volume      = null,
			hdd_volume      = null,
			monitor_enabled = null,
			os              = null;

	public static VirtualMachine getVmByUserAndId(String login, int index, Database database)
	{
		VirtualMachine vm = new VirtualMachine();
		ResultSet rs = Application.server.database.executeQuery("SELECT owner,\n" +
																"       vmname,\n" +
																"       cpuvendor,\n" +
																"       cpufrequency,\n" +
																"       cpucorecount,\n" +
																"       ramvolume,\n" +
																"       hddvolume,\n" +
																"       monitor,\n" +
																"       os\n" +
																"from vms\n" +
																"WHERE owner = '" + login + "';");
		try
		{
			int i = 1;
			while (rs.next() && i <= index)
			{
				//Retrieve by column name
				extractVmFromSet(rs, vm);
				i++;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return vm;
	}

	public static List<VirtualMachine> getAllVirtualMachines(ResultSet set)
	{
		ArrayList<VirtualMachine> vms = new ArrayList<>();
		try
		{
			while (set.next())
			{
				//Retrieve by column name
				VirtualMachine vm = new VirtualMachine();
				extractVmFromSet(set, vm);
				vms.add(vm);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return vms;
	}

	private static void extractVmFromSet(ResultSet set, VirtualMachine vm) throws SQLException
	{
		vm.vm_name = set.getString("vmname");
		vm.cpu_vendor = set.getString("cpuvendor");
		vm.cpu_frequency = set.getString("cpufrequency");
		vm.cpu_core_count = set.getString("cpucorecount");
		vm.ram_volume = set.getString("ramvolume");
		vm.hdd_volume = set.getString("hddvolume");
		vm.monitor_enabled = set.getString("monitor");
		vm.os = set.getString("os");
	}

	public void addToDatabase(String login)
	{
		String stmt = "INSERT INTO vms " +
					  "(owner," +
					  " vmname," +
					  " cpuvendor," +
					  " cpufrequency," +
					  " cpucorecount," +
					  " ramvolume," +
					  " hddvolume," +
					  " monitor," +
					  " os)" +
					  "VALUE ('" + login +
					  "','" + vm_name +
					  "','" + cpu_vendor +
					  "','" + cpu_frequency +
					  "','" + cpu_core_count +
					  "','" + ram_volume +
					  "','" + hdd_volume +
					  "','" + monitor_enabled +
					  "','" + os +
					  "');";
		Application.server.database.executeUpdate(stmt);
	}

	public void removeFromDatabase(String login)
	{
		String stmt =
				"DELETE FROM vms " +
				"WHERE owner='" + login +
				"' AND vmname='" + vm_name +
				"' AND cpuvendor='" + cpu_vendor +
				"' AND cpufrequency='" + cpu_frequency +
				"' AND cpucorecount='" + cpu_core_count +
				"' AND ramvolume='" + ram_volume +
				"' AND hddvolume='" + hdd_volume +
				"' AND monitor='" + monitor_enabled +
				"' AND os='" + os +
				"';";
		Application.server.database.executeUpdate(stmt);
	}

	public String getHtmlTableRow(int index)
	{
		return "<tr><td>" + index +
			   "</td><td>" + vm_name +
			   "</td><td>" + cpu_vendor.substring(0, 1).toUpperCase() + cpu_vendor.substring(1) +
			   "</td><td>" + cpu_frequency
					   .replace("khz", "KHz")
					   .replace("mhz", "MHz")
					   .replace("ghz", "GHz") +
			   "</td><td>" + cpu_core_count +
			   "</td><td>" + ram_volume + " GB" +
			   "</td><td>" + hdd_volume + " GB" +
			   "</td><td>" + monitor_enabled.equals("1") +
			   "</td><td>" + os +
			   "<td><button id=\"edit_vm\" onclick=\"edit_vm(" +
			   index + ")\">Edit VM</button>" +
			   "</td><td><button id=\"remove_vm\" onclick=\"remove_vm(" +
			   index + ")\">Remove VM</button></td>" +
			   "</td></tr>";
	}

	public String getHttpRequestArgumentString()
	{
		return "vmname=" + vm_name +
			   "&cpuvendor=" + cpu_vendor +
			   "&cpufrequency=" + cpu_frequency +
			   "&cpucorecount=" + cpu_core_count +
			   "&ramvolume=" + ram_volume +
			   "&hddvolume=" + hdd_volume +
			   "&monitor=" + monitor_enabled +
			   "&os=" + os;
	}
}
