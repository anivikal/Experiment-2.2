"""
FastMCP Server for Current Date and Time

A simple MCP server that provides tools to get the current date and time.
"""

from datetime import datetime
from zoneinfo import ZoneInfo
from fastmcp import FastMCP

# Create the FastMCP server
mcp = FastMCP("DateTime Server")


@mcp.tool()
def get_current_datetime() -> str:
    """
    Get the current date and time in ISO format.
    
    Returns:
        str: Current date and time in ISO 8601 format (e.g., "2024-01-15T14:30:00")
    """
    now = datetime.now()
    return now.isoformat()


@mcp.tool()
def get_current_date() -> str:
    """
    Get the current date.
    
    Returns:
        str: Current date in YYYY-MM-DD format (e.g., "2024-01-15")
    """
    today = datetime.now().date()
    return today.isoformat()


@mcp.tool()
def get_current_time() -> str:
    """
    Get the current time.
    
    Returns:
        str: Current time in HH:MM:SS format (e.g., "14:30:00")
    """
    now = datetime.now().time()
    return now.isoformat()


@mcp.tool()
def get_datetime_in_timezone(timezone: str) -> str:
    """
    Get the current date and time in a specific timezone.
    
    Args:
        timezone: The timezone name (e.g., "America/New_York", "Europe/London", "Asia/Tokyo")
    
    Returns:
        str: Current date and time in the specified timezone in ISO 8601 format
    """
    try:
        tz = ZoneInfo(timezone)
        now = datetime.now(tz)
        return now.isoformat()
    except Exception as e:
        return f"Error: Invalid timezone '{timezone}'. Please use a valid timezone name like 'America/New_York', 'Europe/London', etc."


@mcp.tool()
def get_formatted_datetime(format_string: str = "%Y-%m-%d %H:%M:%S") -> str:
    """
    Get the current date and time in a custom format.
    
    Args:
        format_string: Python strftime format string (default: "%Y-%m-%d %H:%M:%S")
                      Common formats:
                      - %Y: 4-digit year
                      - %m: 2-digit month
                      - %d: 2-digit day
                      - %H: 24-hour hour
                      - %M: minute
                      - %S: second
                      - %A: full weekday name
                      - %B: full month name
    
    Returns:
        str: Current date and time in the specified format
    """
    try:
        now = datetime.now()
        return now.strftime(format_string)
    except Exception as e:
        return f"Error: Invalid format string. {str(e)}"


if __name__ == "__main__":
    # Run the server
    mcp.run()
