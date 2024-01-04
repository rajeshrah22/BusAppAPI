import React from 'react';
import Drawer from '@mui/material/SwipeableDrawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';

const SideMenu = ({ drawerWidth, open, handleDrawerClose, handleDrawerOpen }) => {
  return (
    <Drawer variant="permanent" open={true} onClose={() => {}} onOpen={() => {}}>
      <List>
        {['Item 1', 'Item 2', 'Item 3'].map((text, index) => (
          <ListItem button key={text}>
            <ListItemText primary={text} />
          </ListItem>
        ))}
      </List>
    </Drawer>
  )
}

export default SideMenu